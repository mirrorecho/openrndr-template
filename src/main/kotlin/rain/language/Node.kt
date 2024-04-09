package rain.language

import rain.interfaces.LanguageNode
import rain.interfaces.SelectDirection
import rain.utils.autoKey
import kotlin.reflect.KProperty0

open class Node(
    key:String = autoKey(),
): LanguageNode, Item(key) {
    companion object : NodeCompanion<Node>( rootLabel {k->Node(k) } )
    override val label:NodeLabel<out Node> = Node.label

    val selectSelf by lazy { SelectSelf(this) }

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

    fun r(
        label:RelationshipLabel,
        keys:List<String>?=null,
        properties:Map<String,Any>?=null,
        direction: SelectDirection = SelectDirection.RIGHT,
    ): SelectRelationships =
        selectSelf.r(
            label,
            keys,
            properties,
            direction
        )

    fun <T:Node>targets(
        rLabel:RelationshipLabel, // TODO: add default label
        nLabel:NodeLabel<T>,
    ): T? = r(
        rLabel,
        direction = SelectDirection.RIGHT
    ).n(nLabel).first

    fun <T:Node>targetsOrMake(
        rLabel:RelationshipLabel, // TODO: add default label
        nLabel:NodeLabel<T>,
        targetKey: String = autoKey()
    ): T {
        targets(rLabel, nLabel)?.let { return it }
        return nLabel.merge(targetKey).also { this.relate(rLabel, it) }
    }

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
    companion object : NodeCompanion<SpecialNode>(Node.childLabel { k -> SpecialNode(k) })
    override val label: NodeLabel<out SpecialNode> = SpecialNode.label
}