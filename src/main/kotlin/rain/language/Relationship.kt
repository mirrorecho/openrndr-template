package rain.language

import rain.interfaces.LanguageRelationship
import rain.interfaces.RelationshipSelectable
import rain.utils.autoKey

open class Relationship(
    key:String = autoKey(),
    override val label: RelationshipLabel,
    var sourceKey: String,
    var targetKey: String,
): LanguageRelationship, RelationshipSelectable, Item(key) {

    override val context get() = label.context
    override val graph get() = context.graph

    override val selectMe get() = SelectRelationships(keys=listOf(this.key))
    override val labelName get() = label.labelName

    override fun toString():String = "(${source.key} $labelName ${target.key} | $key) $properties"

    override val source: Node by lazy { Node(sourceKey) }
    override val target: Node by lazy { Node(targetKey) }

}

fun relate(
    sourceKey: String,
    rLabel:RelationshipLabel,
    targetKey:String,
    key:String = autoKey()
):Relationship = rLabel.create(sourceKey, targetKey, key)

fun relate(
    sourceNode: Node,
    rLabel:RelationshipLabel,
    targetNode:Node,
    key:String = autoKey()
):Relationship = rLabel.create(sourceNode.key, targetNode.key, key)