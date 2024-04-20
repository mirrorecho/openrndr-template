package rain.patterns.nodes

import rain.interfaces.*
import rain.language.*

import rain.patterns.relationships.*

import rain.utils.autoKey

abstract class Tree<T:Tree<T>>(
    key:String = autoKey(),
): Node(key) {
//    companion object : NodeCompanion<Tree<*>>(Node.childLabel { k -> Tree(k) })
        abstract override val label: NodeLabel<out T>

    val isAlter = false

    var isLeaf: Boolean by this.properties.apply { putIfAbsent("isLeaf", false) }

    abstract val thisTyped: T


    // TODO: how to implement this!!!!
    var cuedBy: Cue? = null
    var parent: T? = null // note, this is redundant, but helpful to avoid re-querying parent from the cuedBy
//    var cueLineage: List<Cue> = listOf()

    private fun getCuedChildren(nParent: T, qCue:SelectNodes):Sequence<T> = sequence {
        qCue.first(Cue.label)?.let {cue ->
            yield(cue[CUES()](label).first().also { it.cuedBy = cue; it.parent=nParent })
            yieldAll(getCuedChildren(nParent, cue[CUES_NEXT()]))
        }
    }

    fun cacheMe() {
        cacheChildren.forEach { propertiesUp; it.cacheMe() }
    }

    val children get() = getCuedChildren(thisTyped, this[CUES_FIRST()])

    val cacheChildren: List<T> by lazy { children.toList() }

    val nodes:Sequence<T> get() = sequence {
        yield(thisTyped)
        children.forEach { yieldAll(it.nodes) }
    }

    val leaves:Sequence<T> get() = sequence {
        if (thisTyped.isLeaf) yield(thisTyped)
        else children.forEach { yieldAll(it.leaves) }
    }

    val previous get() = cuedBy?.let{ it[CUES_NEXT.left(), CUES()].first(label) }
    val next get() = cuedBy?.let{ it[CUES_NEXT(), CUES()].first(label) }

    // replaced with cuePath below
//    override var cachedParentage = listOf<Tree>()

    // TODO: testing!
    //TODO: below assumes that all ancestor properties should carry down... are we sure that's what we want?
    val propertiesUp: Map<String, Any?> by lazy { parent?.propertiesUp.orEmpty() + this.properties }

    fun <T>getUp(name:String):T = propertiesUp[name] as T

//    fun <T:Item>getSelect(select: Select<T> = Select(context=context, selectFrom=this.selectSelf) ) {
//    }

    fun saveDown() {
        nodes.forEach { save() }
    }

//    fun <T>vein(name: String): Sequence<T> = leaves.asSequence().map { it.properties[key] as T }


    val isEmpty: Boolean get() = this.relates(CUES_FIRST).selectKeys().firstOrNull() == null


    fun extend(vararg childTrees: Tree<*>) {

        // creates all Cue nodes for the extension (inc. Contains and Cues relationships)
        val cues = childTrees.map {childTree ->
            Cue.create().also {cue ->
                this.relate(CONTAINS, cue)
                cue.relate(CUES, childTree)
            }
        }

        if (isEmpty)
            // if empty, then create the CuesFirst
                // note... empty check works even after creating the Contains relationships above
                // because the isEmpty logic checks for CUES_FIRST
            CUES_FIRST.create(this.key, cues[0].key)
        else {
            // otherwise create a CuesNext relationship from the existing CuesLast target node to the start of extension cue nodes
            // and remove the CuesLast
            relates(CUES_LAST)(CUES_LAST).first().let {
                CUES_NEXT.create(it.targetKey, cues[0].key)
                it.delete()
            }
        }

        // creates CuesNext relationships between all the Cue nodes
        cues.asIterable().zipWithNext { c, cNext ->
            CUES_NEXT.create(c.key, cNext.key)
        }

        // adds CuesLast relationship at the end
        CUES_LAST.create(this.key, cues.last().key)

    }

    // TODO: confirm this works!
    fun stream(name:String, vararg values: Any?) {
        val childrenIterator = leaves.iterator()
        val valuesIterator = values.iterator()
        while (valuesIterator.hasNext()) {
            if (childrenIterator.hasNext()) {
                childrenIterator.next().apply {
                    properties[name] = valuesIterator.next()
                    save()
                }
            } else {
                extend(
                    Event.create(properties = mapOf(name to valuesIterator.next(), "isLeaf" to true) )
                )
            }
        }
    }

    // TODO: does this work???
    fun setVein(name: String, vararg values:Any) {
        leaves.asSequence().zip(values.asSequence()).forEach { it.first[key] = it.second }
    }


    // TODO: implement
    // abstract val parents: SelectInterface

    // TODO: implement the below
//    # TODO: assume this doesn't need to be serialized?
//    leaf_hooks: Iterable[Callable[["rain.Pattern", "rain.Pattern"], "rain.Pattern"]] = ()
//    vein_hooks: Iterable[Callable[["rain.Pattern", Any, int], Any]] = ()
//    _parentage = ()
//    # TODO: MAYBE consider this
//    # node_hooks: Iterable[Callable[["rain.Pattern", "rain.Pattern"], "rain.Pattern"]] = ()
//


}
