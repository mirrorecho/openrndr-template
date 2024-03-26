package rain.rndr

import rain.utils.*
import rain.interfaces.*
import rain.language.*
import  rain.machines.*
import rain.patterns.*

// OLD TODOS:
// TODO: combine Machine and MachineFunc?
// TODO: plan for connecting MachineFuncs to Machine via relationships
// TODO: maybe this class should be abstract?
// TODO: does this class have any purpose at all anymore now that renderOp also implemented on machineFunc?


// TODO: reconfigure so Act type param not needed at class level, only at fun level
abstract class RndrMachine<CBT:CellBuilder>(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): Machine, Leaf(key, properties, context) { // TODO: is Leaf the best parent class? (Relationships might not be simple tree patterns.)

    fun createTrigger(score: Score, time:Double, properties: Map<String, Any?>): Trigger<*> {
        return Trigger(score, this, time, this.properties + properties).apply {
            score.addTrigger(this, time)
        }
    }

    fun <MT:RndrMachine<*>>makeAct(tr:Trigger<MT>): Act<MT> {
        return Act(tr)
    }

    fun <RMT:RndrMachine<*>>relatedMachine(relation:String) = RelatedMachine<RMT>(this, relation)

    fun cell(
        key:String = autoKey(),
        act:String? = this.key,
        properties: Map<String, Any?> = mapOf(),
        context: ContextInterface = LocalContext,
        callback: CBT.()->Unit
    ): Cell {
        Cell(key, properties, context).apply {
            setVeinCycle("machine", )
            act?.let { setVeinCycle("act", it) }
            build(callback)
            createMe()
            return this
        }
    }

    var single: Boolean by this.properties

}


class RelatedMachine<RMT:RndrMachine<*>>(
    val source: RndrMachine<*>,
    val relation: String,
    val targetKey: String? = null
) {

    val target:RMT by lazy {source.targetsOrMakeAs(relation, source.primaryLabel, targetKey)}

    // TODO is the return type OK here... why out???
    // TODO: where is this called??????!!!!!
    fun addTargetAct(sourceTrigger:Trigger<*>): Act<out RndrMachine<*>> {
        val targetTrigger = target.createTrigger(sourceTrigger.score, sourceTrigger.runningTime, sourceTrigger.properties)
        val act = targetTrigger.act
        sourceTrigger.act.targetActs[relation] = targetTrigger.act
        return act
    }

    operator fun invoke(sourceAct:Act<*>):Act<RMT> {
        return sourceAct.targetActs[relation] as Act<RMT>
    }


}

//fun <T:Act>createRndrMachine(key:String= autoKey(),  single:Boolean=true, factory: (tr:Trigger<T>)->T): RndrMachine<T> {
//    return RndrMachine<T>(key).apply {
//        this.single = single
//        setFactory(factory)
//        createMe() // TODO: should the create come before or after setFactory?
//    }
//}

