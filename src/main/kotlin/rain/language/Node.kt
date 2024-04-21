package rain.language

import rain.interfaces.*
import rain.utils.autoKey
import kotlin.reflect.KProperty0

open class Node(
    key:String = autoKey(),
): LanguageNode, NodeSelectable, Item(key) {
    companion object : NodeLabel<Node>(Node::class, null, {k->Node(k) })
    override val label:NodeLabel<out Node> = Node

    override val context get() = label.context
    override val graph get() = context.graph

    override val selectMe get() = SelectNodes(keys=listOf(this.key))
    override val labelName get() = label.labelName

    protected open val targetProperties:List<KProperty0<CachedTarget<out Node>>> = listOf()

    fun autoTarget() {
        targetProperties.forEach {
            it.get().apply {
                createIfMissing()
                target?.autoTarget() // cascade down...
            }
        }
    }

    fun <T:Node>cachedTarget(rLabel: RelationshipLabel, nLabel: NodeLabel<T>) =
        CachedTarget(this, rLabel, nLabel)

    // TODO: maybe implement this...?
//    fun <T:Node>targetsOrMake(
//        rLabel:RelationshipLabel, // TODO: add default label
//        nLabel:NodeLabel<T>,
//        targetKey: String = autoKey()
//    ): T {
//        this[rLabel()](nLabel).firstOrNull()?.let { return it }
//        return nLabel.merge(targetKey).also { this.relate(rLabel, it) }
//    }

    // TODO: maybe implement this...?
//    fun invoke()

    fun relate(
        rLabel:RelationshipLabel,
        targetKey:String,
        key:String = autoKey()
    ):Relationship = rLabel.create(this.key, targetKey, key)

    fun relate(
        rLabel:RelationshipLabel,
        targetNode:Node,
        key:String = autoKey()
    ):Relationship = rLabel.create(this.key, targetNode.key, key)

}

// ===========================================================================================================

// just for fiddling around purposes...
open class SpecialNode(
    key:String = autoKey(),
): Node(key) {
    companion object : NodeLabel<SpecialNode>(SpecialNode::class, Node, { k -> SpecialNode(k) })
    override val label: NodeLabel<SpecialNode> = SpecialNode


//    companion object : NodeCompanion<SpecialNode>(Node.childLabel { k -> SpecialNode(k) })
//    override val label: NodeLabel<out SpecialNode> = SpecialNode.label
}