package rain.patterns

import rain.interfaces.*
import rain.language.*
import rain.utils.autoKey

open class Tree(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): Pattern, Node(key, properties, context) {

    override val label = LocalContext.getLabel("Tree", "Pattern") { k, p, c -> Tree(k, p, c) }


    override val isAlter = false

    override val isLeaf = false

    // TODO: make these by lazy

    override val branches: TreeSelect<T> get() = TreeBranchesSelect(context, this)

    override val nodes: TreeSelect<Tree> get() = TreeNodesSelect(context, this)

    override val leaves: TreeSelect<Leaf> get() = TreeLeavesSelect(context, this)

    override var cuePath: CuePath? = null

    // replaced with cuePath below
//    override var cachedParentage = listOf<Tree>()

    val isEmpty: Boolean get() = r(SelectDirection.RIGHT, "CUES_FIRST").first == null


    fun extend(vararg patterns: Pattern) {

        // creates all Cue nodes for the extension (inc. Contains and Cues relationships)
        val cueNodes = patterns.map {
            val cue = Cue().apply { createMe() }

            relate("CONTAINS", cue)
            //Contains(source_key = this.key, target_key = cue.key).createMe()

            cue.relate("CUES", it as Node)
            // Cues(source_key = cue.key, target_key = it.key).createMe()
            cue
        }

        if (isEmpty)
            // if empty, then create the CuesFirst
                // note... empty check works even after creating the Contains relationships above
                // because the isEmpty logic checks for CUES_FIRST
            CuesFirst(source_key = this.key, target_key = cueNodes[0].key).createMe()
        else {
            // otherwise create a CuesNext relationship from the existing CuesLast target node to the start of extension cue nodes
            // and remove the the CuesLast
            val cuesLast = r(SelectDirection.RIGHT, "CUES_LAST").first!! as Relationship
            CuesNext(source_key = cuesLast.target_key, target_key = cueNodes[0].key).createMe()
            cuesLast.delete()
        }

        // creates CuesNext relationships between all the Cue nodes
        cueNodes.asIterable().zipWithNext { c, cNext ->
            CuesNext(source_key = c.key, target_key = cNext.key).createMe()
        }

        // adds CuesLast relationship at the end
        CuesLast(source_key = this.key, target_key = cueNodes.last().key).createMe()

    }
}

open class EventTree(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): EventPattern, Tree(key, properties, context) {

    override val label = LocalContext.getLabel("EventTree", "Tree", "EventPattern", "Pattern") { k, p, c -> EventTree(k, p, c) }

    override var simultaneous: Boolean by this.properties.apply { putIfAbsent("simultaneous", false) }

    override val dur: Double get() =
        branches.asSequence().map { dur }.run {
            if (simultaneous) { max() } else { sum() }
        }


    // TODO: use something like this?
//    operator fun invoke(vararg patterns: Pattern): CellTree {
//        this.extend(*patterns)
//        return this
//    }

    // TODO: implement cell building
//    fun <BT: CellBuilder> build(callback: BT.() -> Unit): Cell {
//        val cb = CellBuilder(this)
//        cb.apply(callback)
//        return this
//    }

}
