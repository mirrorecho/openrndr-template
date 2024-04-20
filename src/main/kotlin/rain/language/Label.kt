package rain.language

import rain.interfaces.*

class NodeLabel<T:Node>(
    override val labelName:String,
    override val factory: (String)->T,
    override val isRoot: Boolean = false,
    parentNames:List<String>,
): NodeSelectable, NodeLabelInterface<T> {
    override val allNames: List<String> = listOf(labelName) + parentNames
    override val selectMe = SelectNodes(labelName=this.labelName)

    override var context: ContextInterface = LocalContext
}


class RelationshipLabel(
    override val labelName:String,
): RelationshipSelectable, RelationshipLabelInterface<Relationship> {
    override val selectMe = SelectRelationships(labelName=this.labelName)
    override val allNames: List<String> = listOf(labelName)

    override var context: ContextInterface = LocalContext

    override fun factory(sourceKey:String, targetKey:String, key:String): Relationship {
        return Relationship(key, this, sourceKey, targetKey)
    }

    operator fun invoke(keys:List<String>?=null, properties: Map<String, Any>?= null, label:NodeLabel<*>?=null) =
        SelectRelationships(labelName=this.labelName, direction=SelectDirection.RIGHT).nodes(keys, properties, label?.labelName)

    operator fun invoke(vararg keys:String, label:NodeLabel<*>?=null) =
        invoke(keys.asList(), null, label)

    operator fun invoke(properties: Map<String, Any>?= null, label:NodeLabel<*>?=null) =
        invoke(null, properties, label)

    fun left(keys:List<String>?=null, properties: Map<String, Any>?= null, label:NodeLabel<*>?=null) =
        SelectRelationships(labelName=this.labelName, direction=SelectDirection.LEFT).nodes(keys, properties, label?.labelName)

    fun left(vararg keys:String, label:NodeLabel<*>?=null) =
        left(keys.asList(), null, label)

    fun left(properties: Map<String, Any>?= null, label:NodeLabel<*>?=null) =
        left(null, properties, label)

}

val TARGETS = RelationshipLabel("TARGETS")

inline fun <reified T:Node>rootLabel(noinline factory:(String)->T): NodeLabel<T> {
    return NodeLabel(
        T::class.simpleName ?: "Item",
        factory,
        true,
        listOf(),
    )
}