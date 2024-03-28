package rain.patterns

import rain.interfaces.*
import rain.language.*

open class CellBuilder(
    val cell:Cell
) {

    //TODO: implement these ...
//    fun vein(key:String): Vein {
//        return Vein(cell, key)
//    }
//
//    fun value(vararg values:Double?): Vein {
//        return vein("value")(*values)
//    }
//    fun dur(vararg values:Double?): Vein {
//        return vein("dur")(*values)
//    }
//    fun animate(vararg values:Double?): Vein {
//        return vein("animate")(*values)
//    }

}

// TODO: implement
//// TODO: play around with this... esp. with property heritage!
//fun <BT:CellBuilder>cell(
//    key:String = rain.utils.autoKey(),
//    machine:String? = null,
//    properties: Map<String, Any?> = mapOf(),
//    context: ContextInterface = LocalContext,
//    callback: BT.()->Unit
//):Cell {
//    Cell(key, properties, context).apply {
////        machine?.let { setVeinCycle("machine", it) }
////        act?.let { setVeinCycle("act", it) }
//        build(callback)
//        createMe()
//        return this
//    }
//}
