package rain.rndr

import rain.utils.*
import org.openrndr.Program
import org.openrndr.animatable.Animatable
import rain.interfaces.ContextInterface
import rain.language.LocalContext


// DECISION 1: these are NOT nodes in the graph (should be arbitrarily able to spin up 1,000s of actions
// whenever, without modifying the underlying graph)
// DECISION 2: Acts are the core helper implementation for interacting with OPENRNDR
// DECISION 3: Acts are animatable

// TODO maybe: consider whether a trigger would ever be reused...
//  that could be an interesting idea with creative possibilities...


abstract class Act(

    val name: String = autoKey(),

    // TODO: any of these needed?
//    val rndrMachine: RndrMachine<*>,
//    val properties: MutableMap<String, Any?> = mutableMapOf(),
//    val score: Score,
//    val program: Program = score.program
//    context: ContextInterface = LocalContext,


    // the key is the relationship name, the value is the Act object to use for that related machine
    // this is mutable for the same reason as properties above
    // ... TODO: implement logic to create this in score creation ... expect this to get NASTY!
    val relatedMachinesToActs: MutableMap<String, Act> = mutableMapOf()

): Animatable() {
    // val machine: String by this.properties // redundant since the MachineFunc object will need to be specified at the time of instantiation

    var dur: Double = 0.0 // TODO: how is this contolled?
    var isRunning: Boolean = false // TODO: used?

//    fun <T>createActProperty(name: String, relationshipLabel: String? = null): ActProperty<T> {
//        return ActProperty(name, this, relationshipLabel)
//    }

    // TODO maybe: consider a playScope object (instead of separately passing score and program)
    //   ... could also keep track of other stuff on it
    open fun render(score: Score, program: Program) { }


//    // TODO maybe: implement these
//    val time: Double by this.properties
//    val startAction: Int by this.properties // 0=nothing, 1=start, 2=free, 3="pause" (op is not running, but still exists)
//    // TODO consider whether endAction is best specified by the trigger implementation (as opposed to the machine)
//    val endAction: Int by this.properties // ditto values as startAction

//    fun start() { this.machineFunc.startAct(this)  }

//    fun stop() { this.machineFunc.stopAct(this) } // assume not needed

    // TODO: used? or through machines?
//    fun relatedAct(relationshipName:String): Act = relatedMachinesToActs[relationshipName]!!

    // TODO: used? or through machines?
//    fun relatedVal(relationshipName:String): Double = relatedAct(relationshipName).properties["value"] as Double

    // TODO this logic should be moved to the score creation (may get nasty)
//    fun getMachine(machinePalette: Palette<RndrMachine>): RndrMachine? = machinePalette[this.machine]

    // TODO: implement this in score creation (may get nasty)
//    fun triggerMachine(machinePalette: Palette<RndrMachine>) {
//        val machineNode = getMachine(machinePalette)
//        if (machine ==null ) println("$machine not found! Could not trigger machine.")
//        else {
//            // TODO: implement the op triggering
//            machineNode.triggerOp( opKey, program, getProperties() )
//        }
//    }
}

open class ValueAct(
    name: String = autoKey(),
    var value: Double = 0.0
): Act(name) {

}