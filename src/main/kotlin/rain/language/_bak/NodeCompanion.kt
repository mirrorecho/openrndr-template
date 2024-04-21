package rain.language._bak
//
//import rain.interfaces.ContextInterface
//import rain.interfaces.NodeSelectable
//import rain.utils.autoKey
//
//// TODO maybe: should this simply be merged into the label so that the companion IS the label?
//open class NodeCompanion<T:Node>(val label: NodeLabel<T> ): NodeSelectable {
//
//    override val context: ContextInterface get() = label.context
//
//    override val labelName = label.labelName
//    override val selectMe = label.selectMe
//
//    fun read(key:String):T {
//        return label.factory(key).apply { context.graph.read(this) }
//    }
//
//    fun merge(key:String = autoKey(), properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T = label.merge(key, properties, block)
//
//    fun create(key:String = autoKey(), properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T = label.create(key, properties, block)
//
////    inline fun <reified CT:T>childLabel(noinline factory:(String)->CT): NodeLabel<CT> {
////        val name = CT::class.simpleName ?: "Item"
////        return NodeLabel(
////            name,
////            factory,
////            parentNames = label.allNames
////        )
////    }
//
//}
//
//// TODO: MAYBE consider this (but unless the benefit is clear, KISS!!!!)
////class ItemHelper<T:LanguageItem>(
////    val item:T,
////    val label:Label<T>
////) {
////    fun applyBlocks(vararg blocks:T.()->Unit):ItemHelper<T> {
////        return this
////    }
////    operator fun invoke(block: T.()->Unit):ItemHelper<T> {
////        block.invoke(item)
////        return this
////    }
////
////    fun yo() {
////        this {
////    }
////
////}
//
