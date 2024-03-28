package rain.machines

import rain.interfaces.*
import rain.language.*
import rain.patterns.*


interface MachinePattern: Pattern {

//    fun reset() { throw NotImplementedError() }

    // NOTE: trigger is key here... it's what fundamentally makes a machine a machine
    // ... i.e. a machine is something that's "trigger-able"
    // TODO maybe use playerContext object (instead of just runningTime)
    fun trigger(event:Event)

}

abstract class Machine(
    key:String = rain.utils.autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): MachinePattern, Leaf(key, properties, context) {

    override val label = LocalContext.getLabel("Printer", "Machine") { k, p, c -> Printer(k, p, c) }

    override fun trigger(event:Event) {
        // TODO: implement
        println("PRINTER $key: " + event.properties)
        println("--------------------------------")
    }

}

open class Printer(
    key:String = rain.utils.autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): MachinePattern, Leaf(key, properties, context) {

    override val label = LocalContext.getLabel("Printer", "Machine") { k, p, c -> Printer(k, p, c) }

    override fun trigger(event:Event) {
        // TODO: implement
        println("PRINTER $key: " + event.properties)
        println("--------------------------------")
    }

}