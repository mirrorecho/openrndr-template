package rain.sandbox.solve

import org.openrndr.animatable.easing.Easing
import rain.language.Palette
import rain.patterns.*
import rain.language.*
import rain.rndr.*

fun solve6() {


    createValues(true,"X", "Y", "H", "S", "V", "A", "STROKE_WEIGHT", "RADIUS",
        "X_MIN", "X_MAX", "Y_MIN", "Y_MAX"
        )
    relate("X", mapOf(
        "RAND_MIN" to "X_MIN",
        "RAND_MAX" to "X_MAX",
    ))
    relate("Y", mapOf(
        "RAND_MIN" to "Y_MIN",
        "RAND_MAX" to "Y_MAX",
    ))

    RndrMachine<Value>("X").apply {
        setProperty("value", 0.5)
        save()
    }

    val position1 = positionMachine("POSITION_1", true, "X", "Y").apply {

    }

    val color1 = Color("COLOR_1").apply {
//        h.init = 90.0 // TODO - implement
    }

    val circle = CircleMachine("CIRCLE_1").apply {
        radius.targetKey = "SHARED_RADIUS"
        createMe()
    }
    circle.cell {
        animate()
    }

//    val circle = circleMachine("CIRCLE_1", true
//        "RADIUS",
//        "POSITION_1",
//        "COLOR_1",
//        "STROKE_WEIGHT",
//        "COLOR_1"
//    )

    println("----------------------------------------------------------------------------")

    val rndrMachines = Palette.fromKeys<RndrMachine<Act>>(
        "X", "Y", "H", "S", "V", "A", "STROKE_WEIGHT", "RADIUS",
        "POSITION_1", "COLOR_1", "CIRCLE_1"
    )

    val c1 = cell("C1", "CIRCLE_1") { // if no act specified, then actName=machineName
        vein("dur")(0.4, 0.6, 2.0, 1.0 )
        vein("RADIUS")(
            ani(20.0, initValue = 0.0, easing = "CubicIn"), // start at 120.0 and animate to 20.0 over the length of dur (1.0)
            ani(9.0, easing="CubicOut"),
            ani(120.0, easing="CubicOut"),
            ani(99.0)
        )
    }

    val c1a = cell<Circle.Builder>("C1", "CIRCLE_1") { // if no act specified, then actName=machineName
        dur(0.4, 0.6, 2.0, 1.0 )
        radius {
            value(20.0, 9.0, 120.0, 99.0)
            randMin()
            randMax()
            animate(0.0, 0.0, 0.0, null)
            animateInit()
            vein("init")(0.0, null, null, null)
            vein("easing")("CubicIn", "CubicOut", "CubicOut", null)
        }
        channel("POSITION") {
            vein("animate")(0.0, 0.0, 0.0, null)
            vein("easing")("CubicIn", "CubicOut", "CubicOut", null)
            channel("X") {
                vein("value")(20.0, 9.0, 120.0, 99.0)
                vein("init")(0.0, null, null, null)
            }
            channel("Y") {
                vein("value")(20.0, 9.0, 120.0, 99.0)
                vein("init")(0.0, null, null, null)
            }
        }
    }

    val p1 = cell("P1", "POSITION_1") { // if no act specified, then actName=machineName
        vein("dur")(2.4, 1.6 )
//        vein("radius")(400.0, 600.0, 90.0, 200.0, 20.0).ani(null, "CubicInOut")
        vein(machine="X", value="X")(
            ani(rand(0.0, 0.0), initValue = 0.5, easing = "CubicIn"), // start at 120.0 and animate to 20.0 over the length of dur (1.0)
            ani(rand(0.0, 1.0), easing="CubicOut"),
            ani(99.0)
        )
        cell(rel="X") {
            vein()()
        }
    }

    val a1 = cell("ALPHA_1", "COLOR_1") {
        vein("dur")(1.1, 2.9)
        vein("A")(
            ani(1.0, initValue = 0.0),
            ani(0.0, easing="CubicOut")
        )
    }

    val par1 = par()(c1, a1)

    val score = Score(rndrMachines)
    score.readPattern(par1)
    score.play()

}



// -------------------------------------------------------------------------------------

