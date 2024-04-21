package rain.language._bak

//import rain.interfaces.*
//
//// A registry of nodes of a given type/label. Used to ensure that queried nodes
//// can return the same object instances (in cases where state management is important)
//
//class NodeRegistry<T: Node>(
//    vararg nodes: T,
//    val label: NodeLabelInterface<T>,
//    val context: ContextInterface = LocalContext
//) {
//    init { this.extend(*nodes) }
//
////    companion object {
////        fun <S:LanguageNode>fromSelect(select: SelectInterface, context: ContextInterface = LocalContext): Palette<S> {
////            val myPalette = Palette<S>(context = context)
////            myPalette.extendBySelect(select)
////            return myPalette
////        }
////
////        fun <S:LanguageNode>fromKeys(vararg keys: String, context: ContextInterface = LocalContext): Palette<S> {
////            val myPalette = Palette<S>(context = context)
////            myPalette.extendByKeys(*keys)
////            return myPalette
////        }
////
////    }
//
//    private val nodes: MutableMap<String, T> = mutableMapOf()
//
//    // TODO: are all these extends actually used?
//
//    fun extend(vararg nodes: T) {
//        nodes.forEach { this.nodes[it.key] = it }
//    }
//
//    fun extend(vararg keys: String) {
//        this.extend(SelectNodes(keys = keys.toList(), context = this.context))
//    }
//
//    fun extend(select:SelectNodes) {
//        select(label).forEach { this.nodes[it.key]=it }
//    }
//
//    fun forEach(action:(T)->Unit) {nodes.forEach {action(it.value)} }
//
//    fun getOrPut(key:String, defaultNode:()->T) = nodes.getOrPut(key, defaultNode)
//
//    operator fun get(key: String): T? = nodes[key]
//
//    operator fun set(key: String, node: T) {nodes[key]=node}
//
//}