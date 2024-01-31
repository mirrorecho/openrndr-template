package rain.patterns

import rain.interfaces.*
import rain.language.Select
import rain.patterns.*

abstract class TreeSelect(
    context:ContextInterface,
    protected val selfNode: Tree,
    ): Select(context = context) {

    // TODO maybe... by lazy appropriate here? (assume yes)
    override val keys by lazy { asSequence().map{it.key}.toList() }

    // child classes must implement asSequence to avoid problems!
    override fun asSequence(): Sequence<Pattern> = getBranchCues().map { throw(NotImplementedError()) }

    fun getAncestors() = this.selfNode.cuePath?.ancestors.orEmpty() + listOf(this.selfNode)

    fun getBranchCues(): Sequence<Cue> = sequence {
        this@TreeSelect.selfNode.r(SelectDirection.RIGHT, "CUES_FIRST").n().first?.let {
            var branchCue = it as Cue?
            while (branchCue != null) {
//                println(branchCue)
                yield(branchCue)
                branchCue = branchCue.r(SelectDirection.RIGHT, "CUES_NEXT").n().first as Cue?
            }
        }
    }

    fun getBranches(): Sequence<Pattern> = getBranchCues().map {
        // TODO: handle branch hooks
        // TODO: test cuesPattern ancestors
        val myCuePath = CuePath(it, this@TreeSelect.getAncestors())
        it.cuesPattern.apply {
            println("setting cuePath for: " + this.toString())
            this.cuePath = myCuePath
        }
    }
}
// ===========================================================================================================

open class TreeBranchesSelect(
    context:ContextInterface,
    selfNode: Tree,
): TreeSelect(context = context, selfNode=selfNode) {

    override fun asSequence(): Sequence<Pattern> = getBranches()

}
// ===========================================================================================================

open class TreeLeavesSelect(
    context:ContextInterface,
    selfNode: Tree,
): TreeSelect(context = context, selfNode=selfNode) {

    override fun asSequence(): Sequence<Leaf> = sequence {
        this@TreeLeavesSelect.getBranches().forEach {
            yieldAll(it.leaves.asTypedSequence())
        }
    }
}
// ===========================================================================================================

open class TreeNodesSelect(
    context:ContextInterface,
    selfNode: Tree,
): TreeSelect(context = context, selfNode=selfNode) {

    override fun asSequence(): Sequence<Pattern> = sequence {
        yield(selfNode)
        this@TreeNodesSelect.getBranches().forEach {
            yieldAll(it.nodes.asTypedSequence())
        }
    }
}

