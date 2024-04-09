package rain.rndr._bak.rndr_bak
//
//import org.openrndr.Program
//import org.openrndr.animatable.Animatable
//import rain.interfaces.ContextInterface
//import rain.language.LocalContext
//import rain.machines.nodes.Machine
//import rain.patterns.nodes.Leaf
//
//interface OpInterface {
//
//    val properties: MutableMap<String, Any?>
//    val machine: RndrMachine2
//    val name: String
//    val program: Program
//
//    var running: Boolean
//
////    val dur:Double // TODO: consider accommodating ops with indeterminate durs...
////    val program: Program // TODO: needed?
//
//    fun start(): OpInterface {
//        running = true
//        return this
//    }
//
//    fun stop(): OpInterface {
//        running = false
//        machine.cleanup(this)
//        return this
//    }
//}
//
//open class RndrAnimateOp(
//    override val machine: RndrMachine2,
//    override val name: String,
//    override val program: Program,
//    override val properties: MutableMap<String, Any?>,
//): OpInterface, Animatable() {
//
//    override var running: Boolean = false
//
//}
//
//open class RndrMachine2(
//    key:String = rain.utils.autoKey(),
//    properties: Map<String, Any?> = mapOf(),
//    context: ContextInterface = LocalContext,
//): Machine, Leaf(key, properties, context) {
//
//
//    val poly: Boolean by this.properties.apply { putIfAbsent("poly", true) }
//
//    fun opFactory(program: Program, properties: MutableMap<String, Any?>): OpInterface {
//
//    }
//
//    val ops = mutableMapOf<String, RndrOp>()
//
//    override fun render() {
//        ops.forEach {
//            if (it.value.running) it.value.render() }
//    }
//
//    open fun render(op:OpInterface) {
//        println("rendering: " + op.name)
//    }
//
//    override fun reset() {
//        // TODO: implement
//        throw NotImplementedError()
//    }
//
//    open fun cleanup(op: OpInterface) {}
//
//    open fun trigger(runningTime:Double, program: Program, triggerProperties: Map<String, Any?>): RndrOp {
//
//        // TODO: naming? and this is a nasty way to deal with poly...
//        val opName: String = if (this.poly) triggerProperties["name"] as String? ?: rain.utils.autoKey() else this.key
//
//        val combinedProperties = this.properties.toMutableMap().apply {putAll(triggerProperties)}
//
////        println(opName)
//        return (ops.getOrPut(opName)
//        {opFactory(this, opName, program, combinedProperties).start()}
//                ).apply { reTrigger(combinedProperties) }
//    }
//
//
//    // TODO: implement trigger, which can either triggerOn, triggerOff, or update
//
//    // TODO: should this immediately kill the instance? (current implementation) Or trigger a process to eventually kill?
//    fun triggerOff(op: RndrOp) {
//        ops.remove(op.stop().name)
//    }
//}
//
//
//// TODO: maybe this isn't even needed!
//open class RndrAnimate(key:String = rain.utils.autoKey(),
//                  properties: Map<String, Any?> = mapOf(),
//                  context: ContextInterface = LocalContext,
//): RndrMachine<RndrAnimateOp>(key, properties, context), ShapeInterface {
//
//    abstract class Op(
//        override val machine: RndrAnimate,
//        override val program: Program,
//        val properties: MutableMap<String, Any?>, // TODO: reconsider using properties?
//    ):RndrOp
//
//    override fun opFactory(machine: RndrMachine<Op>, program: Program, properties: MutableMap<String, Any?>): Op {
//        return RndrAnimateOp(machine, program, properties.toMutableMap())
//    }
//}
//
