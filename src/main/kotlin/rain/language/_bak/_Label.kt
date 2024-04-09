package rain.language._bak
//
//import rain.interfaces.*
//
//// the POINT of a Label:
//// MOST IMPORTANT: a factory for a type of language item ->
//// ... given data for the item (key, properties), instantiates that item
//
//class _Label<T:LanguageItem>(
//    override var labels: List<String> = listOf(), // TODO set this automatically based on T
//    override val factory: (key:String, properties: Map<String, Any?>, context:ContextInterface)-> T,
//
////  TODO: would this be used?
////  override val context:ContextInterface = LocalContext,
//
//):_LabelInterface {
//    override  val primary get() = labels[0]
//
//    // TODO: would this be used?
////    var myType: KClass<T> = Item
//
//    // NOTE: tried something like this (to avoid this kind of setup on every class definition
//    // ... but unable to work it out (maybe it's not possible)
////    inline fun <reified L:T> setup() {
////        this.labels = L::class.supertypes.map { it.toString() }
////        val t = L::class
////        this.factory = {k,p -> t.inv(k,p)}
////    }
//
//    override fun make(key:String, properties: Map<String, Any?>, context: ContextInterface): T {
//        return this.factory(key,properties, context)
//    }
//
//}
//
//

