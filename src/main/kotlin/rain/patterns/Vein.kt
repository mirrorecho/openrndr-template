package rain.patterns

import org.openrndr.animatable.easing.Easing

class Vein(val cell: Cell, val name:String) {

    // TODO: this assumes all veins contain Double values... need to accomodate other types
    operator fun invoke(vararg values: Any?):Vein {
        cell.traverseNames.add(name)
        if (values.filterIsInstance<AnimateEvent>().isNotEmpty()) {
            val animateList: List<AnimateEvent> = values.map {
                if (it is AnimateEvent) it else AnimateEvent(it as Double, null)
            }
            cell.properties[name] = sequence { animateList.forEach { yield(it.value) } }
            cell.properties["$name:animate"] = sequence { animateList.forEach { yield(it.dur) } }
            cell.properties["$name:easing"] = sequence { animateList.forEach { yield(it.easing) } }
            cell.properties["$name:init"] = sequence { animateList.forEach { yield(it.initValue) } }
//            println("===============================")
//            println(cell.properties)
//            println(animSeq.toList())
            cell.traverseNames.add("$name:animate")
            cell.traverseNames.add("$name:easing")
            cell.traverseNames.add("$name:init")
        } else {
            cell.properties[name] = sequenceOf(*values)
        }
        return this
    }

    // TODO: used?
    fun ani( // just a shortcut (since this will be used so much!)
        dur:Double? = 0.0,
        easing: String? = null,
        initValue:Double? = null, // TODO, consider implementing
    ): Vein {
        cell.setVeinCycle("$name:animate", dur) // //TODO: implementation with another vein on the cell, distinguished only by the "name:..." is odd... rethink?
        cell.setVeinCycle("$name:easing", easing)
        initValue?.let { cell.setVeinCycle("$name:init", initValue) }
        return this
    }

}

// TODO maybe: reconsider the name? (may use Event in other context(s) )
class AnimateEvent(
    val value:Double, // value to animate to
    val dur:Double? = 0.0, // animation duration, if null then no animation, if 0.0 then the full event dur, if - then animate the end of the event
    val easing: String? = null, // "None",
    val initValue:Double? = null, // value to initialize to, before animating, if null, no initial value set (start from current value)
    // val delay: Double? = null, // TODO maybe: (consider implementing)
    // val initDur: Double? = null, // TODO maybe: (consider implementing)
    //    val initEasing: Easing = Easing.None, // TODO maybe: (consider implementing)
) {
    val isAnimation: Boolean = dur != null

    val easingTyped = Easing.valueOf(easing ?: "None")

    val durMs = dur?.let {(it * 1000.0).toLong()} ?: 0



}