package rain.sandbox

import rain.interfaces.*
import rain.language.*
import rain.patterns.*
import rain.rndr.*
import kotlin.random.Random

//fun main() {
//
////
//////    var f = LocalContext.make<FancyNode>("FancyNode", "NODEFN123", mapOf("yo" to "MAMA"))
////
//////    val fn = FancyNode("NODE_FN", mapOf("yo" to "MAMA"))
//////    fn.createMe()
//////
//////    val fn2 = FancyNode("NODE_FN2", mapOf("yo" to "YOYO"))
//////    fn2.createMe()
//////
//////    val fn3 = FancyNode("NODE_FN3", mapOf("yo" to "YOYO"))
//////    fn3.createMe()
//////
//////    val rel = Relationship(key="REL1", source_key="NODE_FN", target_key="NODE_FN2")
////////    println(rel.source.key)
//////    rel.createMe()
//////
//////    val fn_a = FancyNode("NODE_FN", )
//////    fn_a.read()
////
//////    val seq = Select(label="FancyNode", properties = mapOf("yo" to "YOYO"), )
//////    seq.asTypedSequence<FancyNode>().forEach { println(it.key) }
////
//////    fn.r(SelectDirection.RIGHT).n().forEach { println(it.key) }
//////    Select(keys = listOf("NODE_FN")).r(SelectDirection.RIGHT).n().asSequence().forEach { println(it.key) }
//////    println("-----------------------------------------------------------")
//////    fn2.r(SelectDirection.LEFT).n().forEach {println(it.key) }
////
////
//////    println(fn.r(SelectDirection.RIGHT).n().first?.key)
//////    println(fn.selectSelf.targets().first?.key)
////
//////    println("-----------------------------------------------------------")
////    // create machines with machine defaults
////
////    Circle("BIG_CIRCLE", mapOf("radius" to 10.0, "x" to 1.0, "y" to 0.0)).createMe()
////    Circle("SMALL_CIRCLE", mapOf("radius" to 2.0)).createMe()
////    Line("LINE1").createMe()
////    Rectangle("RECT1").createMe()
////    Text("TEXT1").createMe()
////
////    println("-----------------------------------------------------------")
////    // create the cell patterns that trigger the machines
////
////    val c1 = Cell("C1",
//////        mapOf("simultaneous" to true)
////    )
////    c1.setVeinCycle("name", "C1") // TODO: is this being used?
////    c1.setVeinCycle("machine", "BIG_CIRCLE")
////    c1.setVeins("dur", 0.0, 4.0, 3.0, 2.0)
////    c1.setVeins("radius", 400.0, 200.0, 20.0, 490.0)
////    c1.setVeins("x", 1.0, 0.9, 0.4, 1.0)
////    c1.setVeins("y", 1.0, 0.1, 0.1, 1.0)
////    c1.setVeins("gate", true, true, true, false)
//////    c1.setVeins("fillH", 0.0, 200.0, 20.0)
////    c1.createMe()
////
////    val c2 = Cell("C2")
////    c2.setVeinCycle("name", "C2") // TODO: is this being used?
////    c2.setVeinCycle("machine", "TEXT1")
////    c2.setVeinCycle("text", "Yo Mama")
////    c2.setVeins("dur", 2.0)
////    c2.createMe()
////
////    println("-----------------------------------------------------------")
////    // arrange the cell patterns into a piece
////
////    val t1 = CellTree("T1")
////    t1.createMe()
////    t1.extend(c1, c2)
////
////    val t2 = CellTree("T2")
////    t2.createMe()
////    t2.extend(c2, c1)
////
////    val t = CellTree("T",
////        mapOf("simultaneous" to true)
////    )
////    t.createMe()
////    t.extend(t1, t2)
////
////    println("-----------------------------------------------------------")
////
////    println(t.simultaneous)
////
////    println("-----------------------------------------------------------")
////
////    val rndrMachines = Palette.fromKeys<RndrMachine>("BIG_CIRCLE", "SMALL_CIRCLE", "LINE1", "RECT1", "TEXT1")
////
////    val player = RndrPlayer(t, rndrMachines)
////
////    println("-----------------------------------------------------------")
////
////    player.play()
////
//////    fun yoDict(vararg pairs: Pair<String, Any?>): Map<String, Any?> = pairs.toMap()
//////
//////    yoDict("fo" to null, "fan" to 4)
////
//////    val s = Select(keys=listOf("T", "T1", "T2")).toPalette<CellTree>()
////
////
////
//////    println(t1.r(SelectDirection.RIGHT, "CUES_FIRST").first)
////
//////    println(t1.branches.getBranchCues().toList())
//////    println(t1.branches.getBranchCues().toList())
//////    t.branches.forEach { println(it.key) }
//////    t.nodes.forEach { println(it.key) }
//////    t.leaves.forEach { println(it.key) }
//////
//////    t.veins.forEach { println(it) }
//
//
////    val c = Circle3().apply {
////        createTarget("RADIUS")
////        createTarget("RADIUS")
////    }
//
//
//    Circle("BIG_CIRCLE", mapOf("radius" to 10.0, "x" to 1.0, "y" to 0.0)).createMe()
//    Circle("SMALL_CIRCLE", mapOf("radius" to 2.0)).createMe()
//    // TODO: a specific machine func for a single value
//
//    // OPTION 1
//    val constantHeight1 = ValueFunc("CONSTANT_HEIGHT", value=0.6).createMe()
//    val cs1 = Circle("SMALL_CIRCLE").createMe().targets(
//        ValueFunc(value=10.0).createMe().targeting("RADIUS"),
//        Position().createMe().targeting("POSITION").targets(
//            constantHeight1.targeting("POSITION_X"),
//            ValueFunc(value=10.0).targeting("POSITION_Y").createMe()
//        )
//    )
//    //TODO: figure out triggering and ops
//
//    println("----------------------------------------------------------------------------")
//
//    // OPTION 2
//    val constantHeight2 = ValueFunc("CONSTANT_HEIGHT", value=0.6).apply { createMe() }
//    val cs2 = Circle("SMALL_CIRCLE").apply {createMe()
//        radius = ValueFunc(value=10.0).apply { createMe() }
//        position = Position().apply { createMe()
//            x = ValueFunc(value=10.0).apply { createMe() }
//            y = constantHeight2
//        }
//    }
//    //TODO: figure out triggering and ops
//    val c1 = Cell("C1",
////        mapOf("simultaneous" to true)
//    )
//    val h1 = Cell()
//    h1.addVein(mapOf(
//        "y"
//    ))
//
//    c1.setVeinCycle("op", "C1") // TODO: is this being used?
//    c1.setVeinCycle("machine", "SMALL_CIRCLE")
//    c1.setVeins("dur", 0.0, 4.0, 3.0, 2.0)
//    c1.setVeins("radius", 400.0, null, null, 490.0)
//    c1.setVeins("x", 1.0, 0.9, 0.4, 1.0)
//    c1.setVeins("y", 1.0, 0.1, 0.1, 1.0)
////    c1.setVeins("gate", true, true, true, false)
////    c1.setVeins("fillH", 0.0, 200.0, 20.0)
//    c1.createMe()
//
//
//
//    println("----------------------------------------------------------------------------")
//    // OPTION 3
//
//    val constantHeight = makeValue("CONSTANT_HEIGHT", 0.0)
//    val cs3 = Circle("SMALL_CIRCLE",
//        radius = makeValue(10.0),
//        position = makePosition(x=makeValue(0.5), y=constantHeight),
//    ).apply { createMe() }
//
//    println("----------------------------------------------------------------------------")
//    // OPTION 4
//
//    // TODO... naming across the board below is hard to follow... what's a Machine vs something else?
//
//    // TODO generally... how would a MachineTree be used in the RndrMachine context?
//
//    val sharedHeight = funcDouble("SHARED_HEIGHT", 0.0)
//    val cs3 = Circle("SMALL_CIRCLE").make {
//        // NOTE that hint is only defined in the context of the machine! (Circle in this case)
//        // TODO: as I iterate all this, keep the design pattern here where something like "glue" is
//        //  a method in the context of the RnderMachine
//        radius = glue("SIZE", 10.0) // shortcut for making a new funcDouble
//        strokeWeight = glue(0.4)
//        // TODO: note that this glue assumes that x would be one of the Circle's properties (not the Position's)
//        position = funcPosition(x=glue(0.5), y=glue(sharedHeight))
//    }
//
//    val opPars = mutableListOf<Par>()
//
//    // TODO: rather can creating 200 copies of pattern nodes... just create a machine that
//    //  can fire many triggers to other machines!
//    //  (OR.. implement messaging that accommodates spinning up many machine ops... although that approach seems
//    //  restrictive)
//
//    repeat(200) {
//        // TODO also: does it make sense for the op to be instantiated at the point of creating
//        //  the pattern nodes? Instead, instantiate it at the point of triggering the machine?
//
//        op() { // this = Op (could optionally assign it a key)
//            // TODO maybe: consider whether this should even require an explicit dur... instead just
//            //  trigger on, then off at a later time (which would depend on other machines)
//            val hh = opMachineCell("SHARED_HEIGHT") {
//                dur(3.0, 3.0)
//                value(0.9, 0.4)
//            }
//            val ss = opMachineCell("SIZE") {
//                dur(1.0, 3.0, 2.0)
//                value(0.01, Random.nextDouble(), 0.02)
//            }
//            val cc = opMachineCell("SMALL_CIRCLE") {
//                dur(6.0) // NOTE, dur explicitly handled as shortcut to setVeins("dur", ...)
//                setVeins("x", Random.nextDouble())
//            }
//            // TODO: why not just make one big par (instead of a par of pars)? (at least in this model)
//            opPars.add(par(
//                seq(hh, hh),
//                seq(ss, ss),
//                cc,
//            ))
//        }
//
//        // TODO: make another op with a bunch of "SHARED_CIRCLE"s??
//
//    }
//    val allPar = par(**opPars)
//
//    val rndrMachines = Palette.fromKeys<RndrMachine>("SHARED_HEIGHT", "SIZE", "SMALL_CIRCLE")
//
//    val player = RndrPlayer(allPar, rndrMachines)
//
//    player.play()
//
//
//    println("----------------------------------------------------------------------------")
//    // OPTION 5
//
//
//    // manages operations of machines (instead of doing this in context)
//    // - easily bundle ops together (i.e. for all related ops for Circle machines)
//    // - BUT, also allow easy way to use the same op in multiple places
//    // - helps create pattern graph material (cells, trees, etc.), with op-specific information
//
//    // consider this question: is the MachineOperator really about
//    //  (1) the construction of the patterns?
//    //  or (2) the playing of the patterns?
//    //  or both?
//
//    //  ... ANSWER: it's the SCORE!!!!!!!!!!!!!!!!!!!!!!
//
//    class MachineOperator() {
//
//    }
//
//    val operator = MachineOperator()
//
//    // just thinking through the triggers here...
//
//
//
//    // TODO: create necessary machine nodes
//
//    val hh = Cell("HEIGHT_BOUNCE").make(machine: "SHARED_HEIGHT") {
//        dur(3.0, 3.0)
//        value(0.9, 0.4)
//    }
//    val ss = opMachineCell("SIZE") {
//        dur(1.0, 3.0, 2.0)
//        value(0.01, Random.nextDouble(), 0.02)
//    }
//    val cc = opMachineCell("SMALL_CIRCLE") {
//        dur(6.0) // NOTE, dur explicitly handled as shortcut to setVeins("dur", ...)
//        setVeins("x", Random.nextDouble())
//    }
//    // TODO: why not just make one big par (instead of a par of pars)? (at least in this model)
//    opPars.add(par(
//        seq(hh, hh),
//        seq(ss, ss),
//        cc,
//    ))
//
//    println("----------------------------------------------------------------------------")
//    println("----------------------------------------------------------------------------")
//    println("----------------------------------------------------------------------------")
//
//    val heightMachine = ValueFunc("SHARED_HEIGHT", 1.0).createMe()
//    val alphaMachine = ValueFunc("SHARED_ALPHA", 1.0).createMe()
//    val strokeColorMachine = Color("STROKE_COLOR",
//        h = local<Double>("h", 1.0),
//        s = local<Double>("s", 1.0),
//        v = local<Double>("v", 1.0),
//        a = rel<ValueFunc>("SHARED_ALPHA"),
//    ).createMe()
//    val position1Machine = Position("POSITION_1",
//        x = local<Double>("x"),
//        y = rel<Double>("SHARED_HEIGHT"),
//    ).createMe()
//
//    val circle1Machine = Circle(
//        "CIRCLE_1",
//        position = rel<Position>("POSITION_1"),
//        fillColor = composite<Color>(
//            h = local("fill_h", 1.0), // TODO: OK to leave off (i.e. defaulting to Double)?
//            s = local<Double>("fill_s", 1.0),
//            v = local<Double>("fill_v", 1.0),
//            a = rel<ValueFunc>("SHARED_ALPHA", name="FILL_SHARED_ALPHA"), // if set to "SHARED_ALPHA_1, then this would not have to be specified in the cell (see below)
//        ),
//        strokeColor = rel<Color>("STROKE_COLOR"),
//        strokeWeight = local<Double>("stroke_weight"),
//        radius = local<Double>("radius"),
//    ).createMe()
//
//    // defaults = machineName -> actName -> relName
//    // TODO: how to streamline this?
//    val heightBounce = Cell("HEIGHT_BOUNCE").make(machine="HEIGHT", act="SHARED_HEIGHT") {
//        value(0.9, 0.4)
//        dur(2.0, 1.0)
//    }
//    val strokeColor = Cell("STROKE_COLOR_1").make(machine="STROKE_COLOR", act="STROKE_COLOR_1") {
//        h(0.9, 0.4)
//        dur(2.0, 1.0)
//    }
//    val p1 = Cell("P1").make(machine="POSITION_1") { // if no act specified, then actName=machineName
//        x(0.4, 1.0)
//        dur(2.0, 1.0)
//    }
//    val alpha1 = Cell(key="ALPHA_1").make(machine="SHARED_ALPHA", act="SHARED_ALPHA_1") {
//        value(0.9, 0.0)
//        dur(2.0, 1.0)
//    }
//    val alpha2 = Cell(key="ALPHA_2").make(machine="SHARED_ALPHA", act="SHARED_ALPHA_2") {
//        value(0.0, 0.9)
//        dur(2.0, 1.0)
//    }
//    val circleCell = Cell("CIRCLE_CELL").make(machine="CIRCLE_1", act="C_1_a",
//        rels=mapOf(
//            // ... IMPORTANT!!!!!! note that the key is the machine name
//            // "POSITION_1" to "POSITION_1" // default since same name
//            "FILL_SHARED_ALPHA" to "SHARED_ALPHA_1",
//            "STROKE_COLOR" to "STROKE_COLOR_1",
//        )) {
//        seq(
//            "fill_h", 0.9, 1.0,
//            "fill_s", 1.0, 4.0,
//            "dur",    1.0, 4.0
//        )
//    }
//
//    println("----------------------------------------------------------------------------")
//    println("----------------------------------------------------------------------------")
//    println("----------------------------------------------------------------------------")
//
//    createValues(true,"X", "Y")
//
//    // TODO: how to implement the factory if trigger is an update to an existing act? (actName passed, or a "single" machine?)
//    val position1 = createRndrMachine("POSITION_1", true) { tr ->
//        if (tr.act != null) {
//            tr.act as Position
//        } else {
//            Position(
//                x = tr.triggerRelated("X", properties= mapOf()), // is thos
//                y = tr.triggerRelated("Y", properties= mapOf()),
//            )
//        }
//
//    }
//    // TODO: how to relate POSITION to X, Y
//
//    val circle1 = createRndrMachine {
//        Circle(
//            position = it.triggerRelated("POSITION", properties= mapOf())
//        )
//    }
//    // TODO: how to relate POSITION to X, Y
//
//    circle1.relate("POSITION", position1)
//
//
//
//
//
//
//    open class Yo<T>(
//        key:String = rain.utils.autoKey(),
//        properties: Map<String, Any?> = mapOf(),
//        context: ContextInterface = LocalContext,
//    ): Node(key, properties, context) {
//        override val label = LocalContext.getLabel("Yo", "Machine") { k, p, c -> Yo(k, p, c) }
//    }
//
//    val yo = Yo<String>()
//
//    println("----------------------------------------------------------------------------")
//    println("----------------------------------------------------------------------------")
//    println("----------------------------------------------------------------------------")
//
//
//
//
//
//
//}
