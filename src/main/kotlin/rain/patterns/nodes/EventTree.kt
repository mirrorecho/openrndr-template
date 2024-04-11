package rain.patterns.nodes

import rain.language.NodeCompanion
import rain.language.NodeLabel
import rain.patterns.selects.TreeBranchesSelect
import rain.patterns.selects.TreeLeavesSelect
import rain.patterns.selects.TreeNodesSelect
import rain.patterns.selects.TreeSelect
import rain.utils.autoKey

open class EventTree(
    key:String = autoKey(),
): EventPattern, Tree(key) {
    companion object : NodeCompanion<EventTree>(Tree.childLabel { k -> EventTree(k) })
    override val label: NodeLabel<out EventTree> = EventTree.label

//    override val branches: TreeSelect<out EventTree> get() = TreeBranchesSelect(EventTree.label, this)
//
//    override val nodes: TreeSelect<out EventTree> get() = TreeNodesSelect(EventTree.label, this)
//
//    override val leaves: TreeSelect<out Event> get() = TreeLeavesSelect(Event.label, this)

    override var simultaneous: Boolean by this.properties.apply { putIfAbsent("simultaneous", false) }

    val sumDur: Double get() =
        if (isLeaf) getUp("dur")
        else {
            branches.asSequence().map { sumDur }.run {
                if (simultaneous) { max() } else { sum() }
            }
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
