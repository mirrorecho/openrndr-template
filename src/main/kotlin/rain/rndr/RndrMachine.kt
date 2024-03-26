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
abstract class RndrMachine<AT:Act, CBT:CellBuilder>(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): Machine, Leaf(key, properties, context) { // TODO: is Leaf the best parent class? (Relationships might not be simple tree patterns.)

    abstract fun makeAct(tr:Trigger<AT>): AT

    fun <RMT:RndrMachine<RAT,*>, RAT:Act>relatedMachine(relation:String) = RelatedMachine<RMT, RAT>(this, relation)

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

    fun createTrigger(score: Score, time:Double, properties: Map<String, Any?>): Trigger<AT> {
        return Trigger(score, this, time, this.properties + properties).apply {
            score.addTrigger(this, time)
        }
    }

}


class RelatedMachine<RMT:RndrMachine<RAT,*>, RAT:Act>(
    val source: RndrMachine<*,*>,
    val relation: String,
    var targetKey: String? = null
) {
    fun getMachine():RMT = source.targetsOrMakeAs(relation, source.primaryLabel, targetKey)

    fun getRelatedAct(tr:Trigger<*>):RAT {
        return getMachine().createTrigger(tr.score, tr.runningTime, tr.properties).act
    }
}

//fun <T:Act>createRndrMachine(key:String= autoKey(),  single:Boolean=true, factory: (tr:Trigger<T>)->T): RndrMachine<T> {
//    return RndrMachine<T>(key).apply {
//        this.single = single
//        setFactory(factory)
//        createMe() // TODO: should the create come before or after setFactory?
//    }
//}

