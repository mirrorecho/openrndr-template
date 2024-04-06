package rain.sandbox.solve

import rain.patterns.*
import rain.language.*
import rain.rndr.*



fun main() {
    val c = Circle.create()
    c.autoTarget()
    c.radius.target?.value
}
//    val c = cell("C1", "Circle", "CIRCLE1") {
//        this["easing"] = "CubicIn"
//        stream("dur", 4.0, 1.0, 2.0, 6.0)
//        stream("gate", true)
////        last("gate", false)
//    }
//    c.leaves.forEach<Event> {
//        println(it.propertiesUp)
//    }
//}

//
//fun solve6() {
//
//    cellBuilder("CELL_14")<> {
//        dur{0.0}
//        dur(0.4, 0.6, 2.0, 1.0 )
//        position("CENTER_POSITION") {
//            x {
//                dur = 0.0
//                value(20.0, 9.0, 120.0, 99.0)
//            }
//            y {
//                value(20.0, 9.0, 120.0, 99.0)
//            }
//        }
//        radius {
//            value(20.0, 9.0, 120.0, 99.0)
//            randMin()
//            randMax()
//            animate(0.0, 0.0, 0.0, null)
//            animateInit()
//            stream("init")(0.0, null, null, null)
//            stream("easing")("CubicIn", "CubicOut", "CubicOut", null)
//        }
//    }
//
////    val circle = circleMachine("CIRCLE_1", true
////        "RADIUS",
////        "POSITION_1",
////        "COLOR_1",
////        "STROKE_WEIGHT",
////        "COLOR_1"
////    )
//
//    println("----------------------------------------------------------------------------")
//
//    val c1 = cell("C1", "CIRCLE_1") { // if no act specified, then actName=machineName
//        vein("dur")(0.4, 0.6, 2.0, 1.0 )
//        vein("RADIUS")(
//            ani(20.0, initValue = 0.0, easing = "CubicIn"), // start at 120.0 and animate to 20.0 over the length of dur (1.0)
//            ani(9.0, easing="CubicOut"),
//            ani(120.0, easing="CubicOut"),
//            ani(99.0)
//        )
//    }
//
//    val c1a = cell<Circle.Builder>("C1", "CIRCLE_1") { // if no act specified, then actName=machineName
//        dur(0.4, 0.6, 2.0, 1.0 )
//        radius {
//            value(20.0, 9.0, 120.0, 99.0)
//            randMin()
//            randMax()
//            animate(0.0, 0.0, 0.0, null)
//            animateInit()
//            vein("init")(0.0, null, null, null)
//            vein("easing")("CubicIn", "CubicOut", "CubicOut", null)
//        }
//        channel("POSITION") {
//            vein("animate")(0.0, 0.0, 0.0, null)
//            vein("easing")("CubicIn", "CubicOut", "CubicOut", null)
//            channel("X") {
//                vein("value")(20.0, 9.0, 120.0, 99.0)
//                vein("init")(0.0, null, null, null)
//            }
//            channel("Y") {
//                vein("value")(20.0, 9.0, 120.0, 99.0)
//                vein("init")(0.0, null, null, null)
//            }
//        }
//    }
//
//    val p1 = cell("P1", "POSITION_1") { // if no act specified, then actName=machineName
//        vein("dur")(2.4, 1.6 )
////        vein("radius")(400.0, 600.0, 90.0, 200.0, 20.0).ani(null, "CubicInOut")
//        vein(machine="X", value="X")(
//            ani(rand(0.0, 0.0), initValue = 0.5, easing = "CubicIn"), // start at 120.0 and animate to 20.0 over the length of dur (1.0)
//            ani(rand(0.0, 1.0), easing="CubicOut"),
//            ani(99.0)
//        )
//        cell(rel="X") {
//            vein()()
//        }
//    }
//
//    val a1 = cell("ALPHA_1", "COLOR_1") {
//        vein("dur")(1.1, 2.9)
//        vein("A")(
//            ani(1.0, initValue = 0.0),
//            ani(0.0, easing="CubicOut")
//        )
//    }
//
//    val par1 = par()(c1, a1)
//
//    val score = Score(rndrMachines)
//    score.readPattern(par1)
//    score.play()
//
//}



// -------------------------------------------------------------------------------------

