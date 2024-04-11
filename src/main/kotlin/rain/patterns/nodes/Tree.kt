package rain.patterns.nodes

import rain.interfaces.*
import rain.language.*

import rain.patterns.relationships.*
import rain.patterns.selects.*

import rain.utils.autoKey

open class Tree(
    key:String = autoKey(),
): Node(key) {
    companion object : NodeCompanion<Tree>(Node.childLabel { k -> Tree(k) })
    override val label: NodeLabel<out Tree> = Tree.label

    val isAlter = false

    val isLeaf = false

    // TODO maybe: make these by lazy

    val select = TreeSelects(Tree.label, this)

    // replaced with cuePath below
//    override var cachedParentage = listOf<Tree>()

    // TODO: testing!
    //TODO: below assumes that all ancestor properties should carry down... are we sure that's what we want?
    val propertiesUp: Map<String, Any?> get() = properties + cuePath?.properties.orEmpty()

    fun <T>getUp(name:String):T = propertiesUp[name] as T

//    fun <T:Item>getSelect(select: Select<T> = Select(context=context, selectFrom=this.selectSelf) ) {
//    }

    fun saveDown() {
        nodes.forEach { save() }
    }

//    fun <T>vein(name: String): Sequence<T> = leaves.asSequence().map { it.properties[key] as T }

    // TODO: does this work???
    fun setVein(name: String, vararg values:Any) {
        leaves.asSequence().zip(values.asSequence()).forEach { it.first[key] = it.second }
    }


    val isEmpty: Boolean get() = r(CUES_FIRST).first == null


    fun extend(vararg childTrees: Tree) {

        // creates all Cue nodes for the extension (inc. Contains and Cues relationships)
        val cues = childTrees.map {childTree ->
            Cue.create().also {cue ->
                this.relate(CONTAINS, childTree)
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
            r(CUES_LAST).first!!.let {
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
