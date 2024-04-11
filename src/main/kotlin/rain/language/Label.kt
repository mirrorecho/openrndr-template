package rain.language

import rain.interfaces.*

class NodeLabel<T:Node>(
    override val labelName:String,
    override val factory: (String)->T,
    override val isRoot: Boolean = false,
    parentNames:List<String>,
): NodeLabelInterface<T> {
    override val allNames: List<String> = listOf(labelName) + parentNames

    override var context: ContextInterface = LocalContext

    fun select(
        vararg keys:String,
        properties:Map<String,Any>?=null,
    ) = SelectNodes(keys.asList(), properties, labelName)
}


class RelationshipLabel(
    override val labelName:String,
): RelationshipLabelInterface<Relationship> {
    override val allNames: List<String> = listOf(labelName)

    override var context: ContextInterface = LocalContext

    override fun factory(sourceKey:String, targetKey:String, key:String): Relationship {
        return Relationship(key, this, sourceKey, targetKey)
    }

    fun left(vararg keys:String, properties:Map<String,Any>?=null, labelName:String? = null)
        = select(direction = SelectDirection.LEFT).n(*keys, properties=properties, labelName=labelName)

    fun select(
        vararg keys:String,
        direction: SelectDirection? = SelectDirection.RIGHT,
    ) = SelectRelationships(keys.asList(), null, labelName, direction)

    fun select(
        properties:Map<String,Any>?=null,
        direction: SelectDirection? = SelectDirection.RIGHT,
    ) = SelectRelationships(null, properties, labelName, direction)

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