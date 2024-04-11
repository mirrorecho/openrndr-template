package rain.patterns.selects


import rain.interfaces.SelectDirection
import rain.language.*
import rain.patterns.nodes.*
import rain.patterns.relationships.*


open class TreeSelects<T: Tree>(
    val label: NodeLabel<T>,
    private val selfTree: T,
) {
    // is a sequence or list better here????
    private var cuedLineageSequence: Sequence<T> = sequenceOf()

    val branches = getBranches().select(label)
    val nodes = getNodes().select(label)
    val leaves = getLeaves().select(label)

    val cuedLineage get() = cuedLineageSequence.select(Tree.label)

    val parent: T? get() = cuedLineageSequence.lastOrNull()
    val root: T? get() = cuedLineageSequence.firstOrNull()
    val previous get() = selfTree.r(CUES, direction=SelectDirection.LEFT).n(Cue.label).r(CUES_NEXT, direction=SelectDirection.LEFT).n(Cue.label).r(CUES)
//    val previous get() = selfTree.select(CUES.left, CUES_NEXT.left, CUES, label).first
    val next get() = selfTree.select[ CUES.left(), CUES_NEXT(), CUES() ](label).first


    // TODO maybe: implement these seq
//    val aunts: Sequence<Tree>
//    val preceding = Sequence<Tree>
//    val following = Sequence<Tree>
//    val siblings = Sequence<Tree>

    // TODO eventually: figure out how to do this entirely with selects (without needing to instantiate Cue/Node with .first)
    fun getBranches() = sequence {
        selfTree.r(CUES_FIRST).n(Cue.label).first?.let {
            var cue: Cue? = it
            while (cue != null) {
                yield(cue.cues(this@TreeSelects.label)!!.apply {
                    select.cuedLineageSequence  = sequence {
                        yield(selfTree);
                        yieldAll(this@TreeSelects.cuedLineageSequence);
                    }
                })
                cue = cue.r(CUES_NEXT).n(Cue.label).first
            }
        }
    }

    fun getNodes(fromNode:T=selfTree):Sequence<T> = sequence {
        yield(fromNode)
        getBranches().forEach { yieldAll(getNodes(it)) }
    }

    fun getLeaves(fromNode:T=selfTree):Sequence<T> = sequence {
        if (fromNode.isLeaf) yield(fromNode)
        getBranches().forEach { yieldAll(getLeaves(it)) }
    }

}

// ===========================================================================================================

open class TreeLeavesSelect<T: Leaf>(
    label: NodeLabel<out T>,
    selfNode: Tree,
): TreeSelect<T>(label, selfNode) {

    override fun asSequence(): Sequence<T> = sequence {
        this@TreeLeavesSelect.getBranches(Tree.label).forEach {
            println("BRANCH: $it")
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


