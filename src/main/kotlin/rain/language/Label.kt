package rain.language

import rain.interfaces.*

class NodeLabel<T:Node>(
    override val name:String,
    override val factory: (String)->T,
    override val isRoot: Boolean = false,
    parentNames:List<String>,
): NodeLabelInterface<T> {
    override val allNames: List<String> = listOf(name) + parentNames

    override var context: ContextInterface = LocalContext

    fun select(
        keys:List<String>?=null,
        properties:Map<String,Any>?=null,
    ) = SelectNodes(this, keys, properties)
}


class RelationshipLabel(
    override val name:String,
): RelationshipLabelInterface<Relationship> {
    override val allNames: List<String> = listOf(name)

    override var context: ContextInterface = LocalContext

    override fun factory(sourceKey:String, targetKey:String, key:String): Relationship {
        return Relationship(key, this, sourceKey, targetKey)
    }

    fun select(
        keys:List<String>?=null,
        properties:Map<String,Any>?=null,
        direction: SelectDirection? = SelectDirection.RIGHT,
    ) = SelectRelationships(this, keys, properties, direction)

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