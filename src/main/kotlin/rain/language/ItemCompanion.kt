package rain.language

import org.openrndr.extra.parameters.description
import rain.interfaces.*
import rain.utils.autoKey

inline fun <reified T:Node>rootLabel(noinline factory:(String)->T): NodeLabel<T> {
    return NodeLabel(
        T::class.simpleName ?: "Item",
        factory,
        true,
        listOf(),
    )
}

open class NodeCompanion<T:Node>(val label: NodeLabel<T> ) {

    fun read(key:String):T {
        return label.factory(key).apply { context.graph.read(this) }
    }

    fun merge(key:String = autoKey(), block:( T.()->Unit )?=null ):T = label.merge(key, block)

    fun create(key:String = autoKey(), block:( T.()->Unit )?=null ):T = label.create(key, block)

    fun select(
        keys:List<String>?=null,
        properties:Map<String,Any>?=null,
    ) = label.select(keys, properties)

    // TODO: implement
//    fun select(
//        keys:List<String>?=null,
//        properties:Map<String,Any>?=null,
//        direction:SelectDirection?=null,
//    ):Select<T> {
//        return Select(context, )
//    }

    inline fun <reified CT:T>childLabel(noinline factory:(String)->CT): NodeLabel<CT> {
        val name = CT::class.simpleName ?: "Item"
        return NodeLabel(
            name,
            factory,
            parentNames = label.allNames
        )
    }

}

// TODO: MAYBE consider this (but unless the benefit is clear, KISS!!!!)
//class ItemHelper<T:LanguageItem>(
//    val item:T,
//    val label:Label<T>
//) {
//    fun applyBlocks(vararg blocks:T.()->Unit):ItemHelper<T> {
//        return this
//    }
//    operator fun invoke(block: T.()->Unit):ItemHelper<T> {
//        block.invoke(item)
//        return this
//    }
//
//    fun yo() {
//        this {
//    }
//
//}

class NodeLabel<T:Node>(
    override val name:String,
    override val factory: (String)->T,
    override val isRoot: Boolean = false,
    parentNames:List<String>,
):NodeLabelInterface<T> {
    override val allNames: List<String> = listOf(name) + parentNames

    override var context: ContextInterface = LocalContext

    fun select(
        keys:List<String>?=null,
        properties:Map<String,Any>?=null,
    ) = SelectNodes(this, keys, properties)

}


class RelationshipLabel(
    override val name:String
):RelationshipLabelInterface<Relationship> {
    override val allNames: List<String> = listOf(name)

    override var context:ContextInterface = LocalContext

    override fun factory(sourceKey:String, targetKey:String, key:String): Relationship {
        return Relationship(key, this, sourceKey, targetKey)
    }

    fun select(
        keys:List<String>?=null,
        properties:Map<String,Any>?=null,
        direction: SelectDirection? = SelectDirection.RIGHT,
    ) = SelectRelationships(this, keys, properties, direction)

}