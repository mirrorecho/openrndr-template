package rain.patterns._bak

//class Vein(
//    val cell: Cell,
//    val name:String,
//    var isCycle:Boolean = false,
//    ) {
//
//
//
//    // TODO: this assumes all veins contain Double values... need to accomodate other types
//    operator fun invoke(vararg values: Any?):Vein {
//        cell.traverseNames.add(name)
//        cell.properties[name] = if (isCycle) cycleOf(*values) else sequenceOf(*values)
////        }
//        return this
//    }
//
//    // TODO: used?
//    fun ani( // just a shortcut (since this will be used so much!)
//        dur:Double? = 0.0,
//        easing: String? = null,
//        initValue:Double? = null, // TODO, consider implementing
//    ): Vein {
//        cell.setVeinCycle("$name:animate", dur) // //TODO: implementation with another vein on the cell, distinguished only by the "name:..." is odd... rethink?
//        cell.setVeinCycle("$name:easing", easing)
//        initValue?.let { cell.setVeinCycle("$name:init", initValue) }
//        return this
//    }
//
//}
