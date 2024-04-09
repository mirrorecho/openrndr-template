package rain.language

import rain.interfaces.LanguageRelationship
import rain.utils.autoKey

open class Relationship(
    key:String = autoKey(),
    override val label: RelationshipLabel,
    var sourceKey: String,
    var targetKey: String,
): LanguageRelationship, Item(key) {

    override fun toString():String = "(${source.key} $primaryLabel ${target.key} | $key) $properties"

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