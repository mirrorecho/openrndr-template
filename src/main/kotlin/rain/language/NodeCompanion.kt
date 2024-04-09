package rain.language

import rain.utils.autoKey

open class NodeCompanion<T:Node>(val label: NodeLabel<T> ) {

    fun read(key:String):T {
        return label.factory(key).apply { context.graph.read(this) }
    }

    fun merge(key:String = autoKey(), properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T = label.merge(key, properties, block)

    fun create(key:String = autoKey(), properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T = label.create(key, properties, block)

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

