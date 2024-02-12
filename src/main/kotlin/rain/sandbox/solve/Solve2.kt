package rain.sandbox.solve

import org.openrndr.animatable.easing.Easing
import rain.language.Palette
import rain.patterns.Cell
import rain.rndr.*


fun solve2() {
    createValues(true,"X", "Y", "H", "S", "V", "A", "STROKE_WEIGHT", "RADIUS")

    // TODO: this is not being picked up in the trigger properties!
    RndrMachine<ValueAct>("X").apply {
        setProperty("value", 0.25)
        save()
    }

    val position1 = positionMachine("POSITION_1", true, "X", "Y")

    val color1 = colorMachine("COLOR_1", true, "H", "S", "V", "A")

    val circle = circleMachine("CIRCLE_1", true,
        "RADIUS",
        "POSITION_1",
        "COLOR_1",
        "STROKE_WEIGHT",
        "COLOR_1"
    )


    println("----------------------------------------------------------------------------")

//    println(RndrMachine<ValueAct>("RADIUS").apply { read() }.actFactory)

    val rndrMachines = Palette.fromKeys<RndrMachine<Act>>(
        "X", "Y", "H", "S", "V", "A", "STROKE_WEIGHT", "RADIUS",
        "POSITION_1", "COLOR_1", "CIRCLE_1"
    )


//    val c1 = Cell("C1").apply { createMe() }
//    c1.setVeinCycle("machine", "CIRCLE_1")
//    c1.setVeinCycle("act", "CIRCLE_1")
//
//    // TODO: dur  relationship to the animation is confusing here (first value doesn't animate)
//    c1.setVeins("dur", 1.0, 1.0, 8.0, 1.0)
//    c1.setVeins("radius", 499.0, 20.0, 200.0, 20.0)
//    c1.setVeins("radius:easing", "None", "CubicInOut", "None", null)
//    c1.setVeins("radius:animate", 0.5, 1.0, "None", null)

    val c1 = Cell("C1").make(machine="CIRCLE_1") { // if no act specified, then actName=machineName
        it.setVeins("dur", 2.0, 1.0, 8.0, 1.0)
        // TODO: make the below an object, and a node stored in the graph
        // TODO next: helper function to create an ani pattern easily if null pattern, before/after times, and Easing the same
        vein("radius", 400.0, 600.0, 90.0, 200.0, 20.0).ani(null, null, "CubicInOut")
        vein("radius",
            ani(20.0, 499.0), // start at 499.0 and animate to 20.0 over the length of dur (2.0)
            20.0,
            ani(90.0), // equivalent to ani(null, null)
            90.0,
            ani(20.0, 40.0, -0.2, "SineIn")
        )
    }

    val score = Score(rndrMachines)
    score.readPattern(c1)
    score.play()

//    println("YO MAMA")

}

// TODO maybe: reconsider the name? (may use Event in other context(s) )
class AnimateEvent(
    val value:Double, // value to animate to
    val initValue:Double? = null, // value to initialize to, before animating, if null, no initial value set (start from current value)
    val dur:Double? = null, // animation duration, if null then the full event dur, if - then animate the end of the event
// val delay: Double? = null, // TODO maybe: (consider implementing)
// val initDur: Double? = null, // TODO maybe: (consider implementing)
    val easing: String = "None",
//    val initEasing: Easing = Easing.None, // TODO maybe: (consider implementing)
)

fun ani( // just a shortcut (since this will be used so much!)
    value:Double,
    initValue:Double? = null,
    dur:Double? = null,
    easing: String = "None",
): AnimateEvent {
    return AnimateEvent(value, initValue, dur, easing)
}


class AnimateCell(
): Cell(

) {
    var value: Sequence<Double> by this.properties
    var initValue: Sequence<Double?> by this.properties
    var easing: Sequence<String> by this.properties
}

// -------------------------------------------------------------------------------------