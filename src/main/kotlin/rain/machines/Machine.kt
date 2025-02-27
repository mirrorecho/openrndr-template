package rain.machines

import rain.interfaces.*
import rain.language.*
import rain.patterns.*

// TODO: rename to MachineInterface
interface Machine: LanguageNode {

    fun reset() { throw NotImplementedError() }

    // NOTE: trigger is key here... it's what fundamentally makes a machine a machine
    // ... i.e. a machine is something that's "trigger-able"
    // TODO maybe use playerContext object (instead of just runningTime)
    fun trigger(runningTime:Double, properties: Map<String, Any?>) { throw NotImplementedError() }

}


open class Printer(
    key:String = rain.utils.autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): Machine, Leaf(key, properties, context) {

    override val label = LocalContext.getLabel("Printer", "Machine") { k, p, c -> Printer(k, p, c) }


    override fun trigger(runningTime:Double, properties: Map<String, Any?>) {
        println("$runningTime: " + this.key + " " + properties.toString())
    }

}