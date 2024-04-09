package rain.patterns.selects

import rain.language.*
import rain.patterns.nodes.*
import rain.patterns.relationships.*

abstract class TreeSelect<T: Tree>(
    label: NodeLabel<out T>,
    protected val selfNode: Tree,
    ): SelectNodes<T>(label) {

    // TODO maybe... by lazy appropriate here? (assume yes)
    override val keys by lazy { asSequence().map{it.key}.toList() }

    // child classes must implement asSequence to avoid problems!
    override fun asSequence(): Sequence<T> = getBranchCues().map { throw(NotImplementedError()) }

    fun getAncestors() = this.selfNode.cuePath?.ancestors.orEmpty() + listOf(this.selfNode)

    fun getBranchCues(): Sequence<Cue> = sequence {
        this@TreeSelect.selfNode.r(CUES_FIRST).n(Cue.label).first?.let {
            var branchCue: Cue? = it
            while (branchCue != null) {
                yield(branchCue)
                branchCue = branchCue.r(CUES_NEXT).n(Cue.label).first
            }
        }
    }

    fun <BT: Tree>getBranches(label: NodeLabel<out BT>): Sequence<BT> = getBranchCues().map {
        // TODO: handle branch hooks
        // TODO: test cuesPattern ancestors
        val myCuePath = CuePath(it, this@TreeSelect.getAncestors())
        it.cuesTree(label)!!.apply {
//            println("setting cuePath for: " + this.toString())
            this.cuePath = myCuePath
        }
    }
}
// ===========================================================================================================

open class TreeBranchesSelect<T: Tree>(
    label: NodeLabel<out T>,
    selfNode: Tree,
): TreeSelect<T>(label, selfNode) {

    override fun asSequence(): Sequence<T> = getBranches(label)

}
// ===========================================================================================================

open class TreeLeavesSelect<T: Leaf>(
    label: NodeLabel<out T>,
    selfNode: Tree,
): TreeSelect<T>(label, selfNode) {

    override fun asSequence(): Sequence<T> = sequence {
        this@TreeLeavesSelect.getBranches<Tree>(label).forEach {
            yieldAll( TreeLeavesSelect(label, it).asSequence() )
        }
    }
}
// ===========================================================================================================

open class TreeNodesSelect<T: Tree>(
    label: NodeLabel<out T>,
    val typedSelfNode: T,
): TreeSelect<T>(label, typedSelfNode) {

    override fun asSequence(): Sequence<T> = sequence {
        yield(typedSelfNode)
        this@TreeNodesSelect.getBranches(label).forEach {
            yieldAll( TreeNodesSelect(label, it).asSequence() )
        }
    }
}

// ===========================================================================================================
// TODO: anyway we can avoid these?
class TreeSelectEmpty<T: Tree>(
    label: NodeLabel<out T>,
    selfNode: Tree,
): TreeSelect<T>(label, selfNode) {
    override fun asSequence(): Sequence<T> = sequenceOf()
}

open class TreeSelectSelf<T: Tree>(
    label: NodeLabel<out T>,
    val typedSelfNode: T,
): TreeSelect<T>(label, typedSelfNode) {
    override fun asSequence(): Sequence<T>  = sequence {
        yield(this@TreeSelectSelf.typedSelfNode)
    }
}


