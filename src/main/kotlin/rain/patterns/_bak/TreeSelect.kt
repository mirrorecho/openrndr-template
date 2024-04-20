package rain.patterns._bak

import rain.interfaces.NodeSelectable
import rain.interfaces.SelectInterface
import rain.language.*
import rain.patterns.nodes.*
import rain.patterns.relationships.*

//fun <T:Tree>Sequence<T>.select():SelectTrees<T> =
//    SelectTrees(keys = this.toList().map { it.key } )
//
//open class SelectTrees<T: Tree>(
//    keys: List<String>? = null,
//    properties: Map<String, Any?>? = null,
//    labelName: String? = null,
//    private val fromTrees: SelectTrees<T>,
//): SelectNodes(keys, properties, labelName, fromTrees) {
//    // is a sequence or list better here????
//    override val selectMe get() = fromTrees
//
//    private var cineageSequence: Sequence<T> = sequenceOf()
//
//    val branches = getBranches().select()
//    val nodes = getNodes().select()
//    val leaves = getLeaves().select()
//
//    val cuedLineage get() = cuedLineageSequence.select()
//    val parent: T? get() = cuedLineageSequence.lastOrNull()
//    val root: T? get() = cuedLineageSequence.firstOrNull()
//
////    val parents get() = selectMe[CUES.left(), CONTAINS.left()]
////    val previous get() = selectMe[CUES.left(), CUES_NEXT.left(), CUES()]
////    val next get() = selectMe[CUES.left(), CUES_NEXT(), CUES()]
//
//    // TODO maybe: implement these seq
////    val aunts: Sequence<Tree>
////    val preceding = Sequence<Tree>
////    val following = Sequence<Tree>
////    val siblings = Sequence<Tree>
//
//    fun getQBranch(q:SelectNodes):Sequence<SelectTrees<T>> = sequence {
//        yieldAll(q[CUES()].selectKeys())
//    }
//
//    // TODO eventually: figure out how to do this entirely with selects (without needing to instantiate Cue/Node with .first)
//    fun getBranches():Sequence<SelectTrees<T>> = sequence {
//
//        var qCues = fromTrees[CUES_FIRST()]
//        var qBranch = getQBranch(qCues)
//        while (qBranch.notEmpty()) {
//            yield(qBranch)
//            branch.apply {
//                this.select.cuedLineageSequence= sequence {
//                    yield(selfTree);
//                    yieldAll(this@TreeSelects.cuedLineageSequence);
//                }
//            }
//            qCues = qCues[CUES_NEXT()]
//            qBranch = getQBranch(cueQuery)
//        }
//    }
//
//    fun getNodes(fromSelect:SelectTrees<T> = selectMe):Sequence<T> = sequence {
//        yield(fromSelect.)
//        getBranches().forEach { yieldAll(getNodes(it)) }
//    }
//
//    fun getLeaves(fromNode:T=selfTree):Sequence<T> = sequence {
//        if (fromNode.isLeaf) yield(fromNode)
//        getBranches().forEach { yieldAll(getLeaves(it)) }
//    }
//
//}
//

