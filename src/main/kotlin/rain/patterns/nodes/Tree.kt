package rain.patterns.nodes

import rain.interfaces.*
import rain.language.*

import rain.patterns.relationships.*

import rain.utils.autoKey

abstract class Tree(
    key:String = autoKey(),
): Node(key) {
//    companion object : NodeLabel<Tree>(Tree::class, Node, { k -> Tree(k) })
    abstract override val label: NodeLabel<out Tree>

    val isAlter = false

    var isLeaf: Boolean by this.properties.apply { putIfAbsent("isLeaf", false) }

    abstract val lineage: TreeLineage<out Tree>

    abstract val lineageProperties: List<String>

    // replaced with cuePath below
//    override var cachedParentage = listOf<Tree>()

//    fun <T:Item>getSelect(select: Select<T> = Select(context=context, selectFrom=this.selectSelf) ) {
//    }

    fun saveDown() {
        lineage.nodes.forEach { save() }
    }


    val isEmpty: Boolean get() = this.relates(CUES_FIRST).selectKeys().firstOrNull() == null


    fun extend(vararg childTrees: Tree) {

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
        val childrenIterator = lineage.leaves.iterator()
        val valuesIterator = values.iterator()
        while (valuesIterator.hasNext()) {
            if (childrenIterator.hasNext()) {
                childrenIterator.next().apply {
                    this.tree.properties[name] = valuesIterator.next()
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
        lineage.leaves.asSequence().zip(values.asSequence()).forEach { it.first.tree[key] = it.second }
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
