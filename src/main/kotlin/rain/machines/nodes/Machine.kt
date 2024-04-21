package rain.machines.nodes

import rain.language.*
import rain.patterns.nodes.Event


// TODO maybe: inherit from Pattern?
interface MachinePattern {

//    fun reset() { throw NotImplementedError() }

    // NOTE: trigger is key here... it's what fundamentally makes a machine a machine
    // ... i.e. a machine is something that's "trigger-able"
    // TODO maybe use playerContext object (instead of just runningTime)
    fun trigger(event: Event)

}

abstract class Machine(
    key:String = rain.utils.autoKey(),
): MachinePattern, Node(key) {
    companion object : NodeLabel<Machine>(Machine::class, Node, { k -> Node(k) as Machine })
    override val label: NodeLabel<out Machine> = Machine

    override fun trigger(event: Event) {
        // TODO: implement
        println("PRINTER $key: " + event.properties)
        println("--------------------------------")
    }

}

open class Printer(
    key:String = rain.utils.autoKey(),
): MachinePattern, Machine(key) {
    companion object : NodeLabel<Printer>(Printer::class, Machine, { k -> Printer(k) })
    override val label: NodeLabel<out Printer> = Printer

    override fun trigger(event: Event) {
        // TODO: implement
        println("PRINTER $key: " + event.properties)
        println("--------------------------------")
    }

}