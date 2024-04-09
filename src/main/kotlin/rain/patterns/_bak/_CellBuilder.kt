package rain.patterns._bak

//class YoYo<T>(
//    val name:String,
//    val properties: MutableMap<String, Any?> = mutableMapOf(),
//) {
//    operator fun invoke(vararg stream: T, callback:(()->T)? = null) {
//        callback?.let{func->
//            func.invoke().let {v->
//                properties[name] = v
//                println("Setting to: $v")
//            }
//        }
//        if (stream.isNotEmpty()) {
//            println(stream.joinToString(", "))
//        }
//    }
//}

//
//class CellBuilder(
//    val cell: Cell
//) {
//
//    class PropertyHelper(
//        val name:String,
//        val parentBuilder:CellBuilder
//    ) {
//        operator fun invoke(vararg stream: Any?) {
//            parentBuilder.cell.apply {
//                // TODO: logic here for adding/updating events based on stream values
//            }
//        }
//
//        fun value(name:String): Any? = parentBuilder.cell[name]
//    }
//
//    operator fun get(name: String): PropertyHelper {
//        return PropertyHelper(name, this)
//    }
//    operator fun set(name: String, value: Any?) {
//        this.cell[name] = value
//    }
//
//    fun channel(path:String?=null, block:CellBuilder.()->Unit) {
//
//    }
//}
//
//
//
//
////open class CellBuilder(
////    val cell:Cell
////) {
////    //TODO: implement these ...
//////    fun vein(key:String): Vein {
//////        return Vein(cell, key)
//////    }
//////
//////    fun value(vararg values:Double?): Vein {
//////        return vein("value")(*values)
//////    }
//////    fun dur(vararg values:Double?): Vein {
//////        return vein("dur")(*values)
//////    }
//////    fun animate(vararg values:Double?): Vein {
//////        return vein("animate")(*values)
//////    }
////}
//
//
////
////fun <BT:CellBuilder>cell(
////    key:String = autoKey(),
////    context: ContextInterface = LocalContext,
////    callback: BT.()->Unit): Cell {
////    return Cell(key, context = context).apply {
////        createMe()
////        CellBuilder(this).apply(callback)
////    }
////}
////
//
//// TODO: implement
////// TODO: play around with this... esp. with property heritage!
////fun <BT:CellBuilder>cell(
////    key:String = rain.utils.autoKey(),
////    machine:String? = null,
////    properties: Map<String, Any?> = mapOf(),
////    context: ContextInterface = LocalContext,
////    callback: BT.()->Unit
////):Cell {
////    Cell(key, properties, context).apply {
//////        machine?.let { setVeinCycle("machine", it) }
//////        act?.let { setVeinCycle("act", it) }
////        build(callback)
////        createMe()
////        return this
////    }
////}
