package rain.language

import rain.interfaces.ContextInterface
import rain.interfaces.RelationshipLabelInterface
import rain.interfaces.RelationshipSelectable
import rain.interfaces.SelectDirection

class RelationshipLabel(
    override val labelName:String,
): RelationshipSelectable, RelationshipLabelInterface<Relationship> {
    override val selectMe = SelectRelationships(labelName=this.labelName)
    override val allNames: List<String> = listOf(labelName)

    override var context: ContextInterface = LocalContext

    override fun toString() = labelName

    override fun factory(sourceKey:String, targetKey:String, key:String): Relationship {
        return Relationship(key, this, sourceKey, targetKey)
    }

    operator fun invoke(keys:List<String>?=null, properties: Map<String, Any>?= null, label:NodeLabel<*>?=null) =
        SelectRelationships(labelName=this.labelName, direction= SelectDirection.RIGHT).nodes(keys, properties, label?.labelName)

    operator fun invoke(vararg keys:String, label:NodeLabel<*>?=null) =
        invoke(keys.asList(), null, label)

    operator fun invoke(properties: Map<String, Any>?= null, label:NodeLabel<*>?=null) =
        invoke(null, properties, label)

    fun left(keys:List<String>?=null, properties: Map<String, Any>?= null, label:NodeLabel<*>?=null) =
        SelectRelationships(labelName=this.labelName, direction= SelectDirection.LEFT).nodes(keys, properties, label?.labelName)

    fun left(vararg keys:String, label:NodeLabel<*>?=null) =
        left(keys.asList(), null, label)

    fun left(properties: Map<String, Any>?= null, label:NodeLabel<*>?=null) =
        left(null, properties, label)

}

val TARGETS = RelationshipLabel("TARGETS")