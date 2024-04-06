package rain.patterns

import rain.interfaces.*
import rain.language.Select
import rain.patterns.*

abstract class TreeSelect<T:Tree>(
    context:ContextInterface,
    protected val selfNode: Tree,
    ): Select<T>(context = context) {

    // TODO maybe... by lazy appropriate here? (assume yes)
    override val keys by lazy { asSequence().map{it.key}.toList() }

    // child classes must implement asSequence to avoid problems!
    override fun asSequence(): Sequence<T> = getBranchCues().map { throw(NotImplementedError()) }

    fun getAncestors() = this.selfNode.cuePath?.ancestors.orEmpty() + listOf(this.selfNode)

    fun getBranchCues(): Sequence<Cue> = sequence {
        this@TreeSelect.selfNode.r(SelectDirection.RIGHT, "CUES_FIRST").n<Cue>().first?.let {
            var branchCue: Cue? = it
            while (branchCue != null) {
                yield(branchCue)
                branchCue = branchCue.r(SelectDirection.RIGHT, "CUES_NEXT").n<Cue>().first
            }
        }
    }

    fun <BT:Tree>getBranches(): Sequence<BT> = getBranchCues().map {
        // TODO: handle branch hooks
        // TODO: test cuesPattern ancestors
        val myCuePath = CuePath(it, this@TreeSelect.getAncestors())
        it.cuesTree<BT>()!!.apply {
//            println("setting cuePath for: " + this.toString())
            this.cuePath = myCuePath
        }
    }
}
// ===========================================================================================================

open class TreeBranchesSelect<T:Tree>(
    context:ContextInterface,
    selfNode: Tree,
): TreeSelect<T>(context = context, selfNode=selfNode) {

    override fun asSequence(): Sequence<T> = getBranches()

}
// ===========================================================================================================

open class TreeLeavesSelect<T:Leaf>(
    context:ContextInterface,
    selfNode: Tree,
): TreeSelect<T>(context = context, selfNode=selfNode) {

    override fun asSequence(): Sequence<T> = sequence {
        this@TreeLeavesSelect.getBranches<Tree>().forEach {
            yieldAll( TreeLeavesSelect<T>(context, it).asSequence() )
        }
    }
}
// ===========================================================================================================

open class TreeNodesSelect<T:Tree>(
    context:ContextInterface,
    val typedSelfNode: T,
): TreeSelect<T>(context = context, selfNode=typedSelfNode) {

    override fun asSequence(): Sequence<T> = sequence {
        yield(typedSelfNode)
        this@TreeNodesSelect.getBranches<T>().forEach {
            yieldAll( TreeNodesSelect(context, it).asSequence() )
        }
    }
}

