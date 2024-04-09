package rain.patterns.nodes

import rain.interfaces.*
import rain.language.*

import rain.patterns.relationships.*
import rain.patterns.selects.*

import rain.utils.autoKey

open class Tree(
    key:String = autoKey(),
): Pattern, Node(key) {
    companion object : NodeCompanion<Tree>(Node.childLabel { k -> Tree(k) })
    override val label: NodeLabel<out Tree> = Tree.label

    override val isAlter = false

    override val isLeaf = false

    // TODO: make these by lazy

    override val branches: TreeSelect<out Tree> get() = TreeBranchesSelect(Tree.label, this)

    override val nodes: TreeSelect<out Tree> get() = TreeNodesSelect(Tree.label, this)

    override val leaves: TreeSelect<out Leaf> get() = TreeLeavesSelect(Leaf.label, this)

    override var cuePath: CuePath? = null

    // replaced with cuePath below
//    override var cachedParentage = listOf<Tree>()

    val isEmpty: Boolean get() = r(CUES_FIRST).first == null


    fun extend(vararg trees: Tree) {
        println("===============================================")
        println("ADDING ${trees.size} TREES")

        // creates all Cue nodes for the extension (inc. Contains and Cues relationships)
        val cues = trees.map {
            val cue = Cue.create()

            val r = this.relate(CONTAINS, cue)
            println("relating $this to $cue")
            println(r)
            println("--")
            //Contains(source_key = this.key, target_key = cue.key).createMe()

            cue.relate(CUES, it)
            // Cues(source_key = cue.key, target_key = it.key).createMe()
            cue
        }
        cues.forEach {
            println(it)
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

        println("----------------------")

    }
}
