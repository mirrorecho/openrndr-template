package rain.rndr.nodes

import rain.utils.*
import rain.language.*
import rain.machines.nodes.Machine
import rain.machines.nodes.MachinePattern
import rain.patterns.nodes.Event

// OLD TODOS:
// TODO: combine Machine and MachineFunc? (YES, DID IT!)
// TODO: plan for connecting MachineFuncs to Machine via relationships
// TODO: maybe this class should be abstract?
// TODO: does this class have any purpose at all anymore now that renderOp also implemented on machineFunc?
// TODO: reconfigure so Act type param not needed at class level, only at fun level
// TODO: are RndrMachines Laaves?

abstract class RndrMachine(
    key:String = autoKey(),
): MachinePattern, Machine(key) { // TODO: is Leaf the best parent class? (Relationships might not be simple tree patterns.)
    companion object : NodeLabel<RndrMachine>(RndrMachine::class, Machine, { k -> Node(k) as RndrMachine })
    override val label: NodeLabel<out RndrMachine> = RndrMachine


//    TODO: implement below if implementing Pattern interface for Machine ...

//    override val isAlter = false
//
//    override val isLeaf = false
//
//    override val branches = EmptySelect(context)
//
//    // TODO: implement hook logic here?
//    override val leaves get() = EmptySelect(context)
//
//    override val nodes get() = EmptySelect(context)
//
//    override var cuePath: CuePath? = null

    // TODO: useful...?
    // parentMachine?

    override fun trigger(properties: Map<String, Any?>) {
        // TODO: implement gating and such
//        if (event.gate==true) {
//
//        }
    }


    open var single: Boolean by this.properties

//    fun createTrigger(score: Score, time:Double, gate:Boolean?=true, cascade:Boolean=gate?:false,
//                      properties: Map<String, Any?>): Trigger {
//
//        val triggerMachine = if (single) this else
//            context.make<RndrMachine<*>>(
//                primaryLabel, (properties["spawn"] as String?)?: autoKey(), this.properties, context)
//
//        if (!single) {
//            this.relate("SPAWN", triggerMachine)
//
//            targetMachines.asTypedSequence<RndrMachine<*>>().forEach {
//                // TODO: implement cascading properties
//                if (cascade) {
//                    it.createTrigger(score, time, gate, true, mapOf())
//                }
//            }
//        }
//
//        return Trigger(score, triggerMachine, time, gate, properties).apply {
//            score.addTrigger(this, time)
//        }
//
//    }


    // TODO: implement
//    fun cell(
//        key:String = autoKey(),
//        act:String? = this.key,
//        properties: Map<String, Any?> = mapOf(),
//        context: ContextInterface = LocalContext,
//        callback: CBT.()->Unit
//    ): Cell {
//        Cell(key, properties, context).apply {
//            setVeinCycle("machine", )
//            act?.let { setVeinCycle("act", it) }
//            build(callback)
//            createMe()
//            return this
//        }
//    }

}


//class RelatedMachine<RMT:RndrMachine<*>>(
//    val source: RndrMachine<*>,
//    val relation: String,
//    val targetKey: String? = null
//) {
//
//    val target:RMT by lazy {source.targetsOrMakeAs(relation, source.primaryLabel, targetKey)}
//
//    // TODO is the return type OK here... why out???
//    // TODO: where is this called??????!!!!!
//    fun addTargetAct(sourceTrigger:Trigger<*>): Act<out RndrMachine<*>> {
//        val targetTrigger = target.createTrigger(sourceTrigger.score, sourceTrigger.runningTime, sourceTrigger.properties)
//        val act = targetTrigger.act
//        sourceTrigger.act.targetActs[relation] = targetTrigger.act
//        return act
//    }
//
//    operator fun invoke(sourceAct:Act<*>):Act<RMT> {
//        return sourceAct.targetActs[relation] as Act<RMT>
//    }
//
//}

//fun <T:Act>createRndrMachine(key:String= autoKey(),  single:Boolean=true, factory: (tr:Trigger<T>)->T): RndrMachine<T> {
//    return RndrMachine<T>(key).apply {
//        this.single = single
//        setFactory(factory)
//        createMe() // TODO: should the create come before or after setFactory?
//    }
//}

