import org.openrndr.Program
import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector2
import rain.interfaces.ContextInterface
import rain.language.LocalContext
import rain.language.Relationship
import rain.machines.Machine
import rain.patterns.Leaf



//
//
//// testing this out to see how inheritance might play out...
//open class Circle2(
//    key:String = rain.utils.autoKey(),
//    properties: Map<String, Any?> = mapOf(),
//    context: ContextInterface = LocalContext,
//): rain.machines.Printer(key, properties, context) { // NOTE: just using Printer for simplicity sake...
//
//    // TODO: IDEALLY - REMOVE
////    companion object : ItemCompanion() {
////        override val label: Label<Circle2> = Label(
////            factory = { k, p, c -> Circle2(k, p, c) },
////            labels = listOf("Circle2", "RndrShape", "RndrMachine", "Machine"),
////        )
////    }
//
//    // TODO: IDEALLY - REMOVE
////    override val label: LabelInterface get() = Circle2.label
//
//    // TODO: IDEALLY - REMOVE
////    override fun opFactory(machine: RndrMachine, program: Program, properties: MutableMap<String, Any?>): RndrOp {
////        return CircleOp(machine as Circle, program, properties.toMutableMap())
////    }
//
//    // these properties would apply be for any shapes:
//    var x: Double by this.properties.apply { putIfAbsent("x", 200.0) }
//    var y: Double by this.properties.apply { putIfAbsent("y", 200.0) }
//
//    var radius: Double by this.properties.apply { putIfAbsent("radius", 90.0) }
//
//    fun render(op: OpInterface) {
//        super.render()
//
//        op.program.apply {
////            drawer.fill = fill
////            drawer.stroke = stroke
////            drawer.strokeWeight = strokeWeight
//            // TODO: are these defaults OK?
//            drawer.circle(x * op.program.width, y * op.program.height, radius)
//        }
//    }
//
//    class CircleOp(
//        override val machine: Circle,
//        program: Program,
//        properties: MutableMap<String, Any?>,
//    ): RndrAnimationOp(machine, program, properties) {
//
//        var radius: Double = 0.0
//
//        init {
//            animatableMap.putAll(mapOf(
//                "radius" to ::radius,
//            ))
//        }
//
//        override fun render() {
//            super.render()
//
//            this.program.apply {
//                drawer.fill = fill
//                drawer.stroke = stroke
//                drawer.strokeWeight = strokeWeight
//                // TODO: are these defaults OK?
//                drawer.circle(x * program.width, y * program.height, radius)
//            }
//        }
//    }
//}