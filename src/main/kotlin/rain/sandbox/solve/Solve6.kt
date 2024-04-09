package rain.sandbox.solve

import rain.graph.Graph
import rain.interfaces.SelectDirection
import rain.language.*

import rain.patterns.nodes.*
import rain.patterns.relationships.*
import rain.rndr.nodes.*
import rain.rndr.relationships.*


fun main() {

    val graph = LocalContext.graph as Graph

    val n0 = Node.create("N0")
    val n1 = Node.create("N1")
    val n2 = Node.create("N2")
    val r01 = n0.relate(TRIGGERS, n1)
    val r02 = n0.relate(TRIGGERS, n2)
    val r1 = n1.relate(TARGETS, n2, "YO1-2")
    val r2 = n2.relate(TARGETS, n1, "YO2-1")

//    val r1 = relate(n1, TARGETS, n2, "YO1-2")
//    val r1 = TARGETS.create("N1", "N2")
//    val r2 = relate(n2, TARGETS, n1, "YO2-1")

//    SelectRelationships(TARGETS).forEach { println(it) }
    n1.r(TARGETS).forEach { println(it) }

//    val t = Tree.create("T0", mapOf("yo" to 0)) {
//        extend(
//            Tree.create("T1", mapOf("yo" to 1)) {
////                extend(
////                    Leaf.create("T1-1"),
////                    Leaf.create("T1-2", mapOf("yo" to 2)),
//////                    Leaf.create { this["yo"]=3 },
//////                    Leaf.create {  },
////                )
//            },
//            Leaf.create {  },
////            Leaf.create("T4", mapOf("yo" to 4)),
//        )
//    }
//
//    val tSelect = SelectRelationships(CONTAINS)
//
////    println(SelectRelationships(CONTAINS).asSequence().toList().size)
//
////    val tSelect = SelectNodes(Tree.label, listOf("T1", "T2"))
////    val tSelect = SelectNodes(Tree.label, listOf("T1", "T4")).r(CONTAINS).n(Node.label)
////    val tSelect = SelectRelationships(CONTAINS, direction = SelectDirection.RIGHT).n(Node.label).r(CUES)
////    val tSelect = SelectRelationships(TARGETS)
    println("----------------------------------------")
////    tSelect.forEach { println(it); println(" - target: ${it.targetKey}") }
//
    graph.graphRelationships.forEach { println( it ) }

//    t.branches.forEach { println(it.properties) }

//    val c = Cell.create("C1") { machine = Circle.label }.apply {
//        stream("dur", 1.0, 2.0, 1.0, 4.0)
////        channel(RADIUS) {
////            stream("value", 90.0, 20.0, 400.0, 200.0)
////        }
////        channel(RADIUS) {
////            stream("value", 90.0, 20.0, 400.0, 200.0)
////        }
//    }

//    c.nodes.forEach { println(it.properties) }

//    val c = Circle.create("YOMAMA")
////    val c2 = Circle.create()
////    c.relate(Relationship.TARGETS, c2)
//    c.autoTarget()
//    c.radius.target?.apply { this["value"]=400.0; save() }
//
//    val r = Relationship.rndr.RADIUS.select().n(Value.label)
//    val rv = r.first!!
//    println(r.asSequence().toList().size)
//
//    rv["value"] = 20.0
//    rv.save()
//
//    println(rv.properties)
//    c.radius.target = null
//    println(c.radius.target?.get("value"))

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

