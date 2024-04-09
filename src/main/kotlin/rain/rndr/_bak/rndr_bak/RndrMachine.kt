package rain.rndr._bak.rndr_bak

// represents an instance of a machine being operated
//interface RndrOp {
//    val machine: RndrMachine<RndrOp>
//    var running: Boolean
////    val dur:Double // TODO: consider accommodating ops with indeterminate durs...
////    val program: Program // TODO: needed?
//
//    val name: String
//
//    fun start(): RndrOp {
//        running = true
//        return this
//    }
//
//    fun reTrigger(properties: Map<String, Any?>) {}
//
//    fun render() {}
//
//    fun stop(): RndrOp {
//        running = false
//        machine.cleanup(this)
//        return this
//    }
//}
//
//// think of RndrMachine like an sc SynthDef ... it's a blueprint,
//// that also implements the rain Machine interface... triggering the machine
//// creates a new instance of a running "op" (short for operation of machine)
//
//abstract class RndrMachine<T:RndrOp>(
//    key:String = rain.utils.autoKey(),
//    properties: Map<String, Any?> = mapOf(),
//    context: ContextInterface = LocalContext,
//    program: Program,
//    val opFactory: () -> T,
//): Machine, Leaf(key, properties, context) {
//
//    val poly: Boolean by this.properties.apply { putIfAbsent("poly", true) }
//
////    abstract fun opFactory(machine: RndrMachine<T> = this, program: Program, properties: MutableMap<String, Any?>): T
//
//    val ops = mutableMapOf<String, RndrOp>()
//
//    override fun render() {
//        ops.forEach {
//            if (it.value.running) it.value.render() }
//    }
//
//    override fun reset() {
//        // TODO: implement
//        throw NotImplementedError()
//    }
//
////    abstract fun renderInstance(instance: MachineInstance)
//
//    fun cleanup(op: RndrOp) {}
//
//    fun trigger(runningTime:Double, program: Program, triggerProperties: Map<String, Any?>): RndrOp {
//
//        // TODO: naming? and this is a nasty way to deal with poly...
//        val opName: String = if (this.poly) triggerProperties["name"] as String? ?: rain.utils.autoKey() else this.key
//
//        val combinedProperties = this.properties.toMutableMap().apply {putAll(triggerProperties)}
//
////        println(opName)
//        return (ops.getOrPut(opName)
//            {opFactory(this, program, combinedProperties).start()}
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
//
//}
