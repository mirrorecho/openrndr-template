package rain.rndr

import rain.patterns.*
import rain.utils.*

// TODO maybe: consider renaming to Event? (wait to see how this is implemented in SuperCollider implementation)
// TODO: OK To not include type parameter of the Act here????
class Trigger<MT:RndrMachine<*>>(
    val score: Score,
    val rndrMachine: MT,
    val runningTime:Double = 0.0, // TODO: used?
    val gate: Boolean? = true, // true gates the machine on in the score rendering, false off, and null no change
    val properties: Map<String, Any?> = mapOf(),
) {


    val dur: Double by this.properties

    val durMs get() = (dur * 1000.0).toLong()

    fun <P>propertyAs(propertyName:String):P {
        return (properties[propertyName] ?: rndrMachine.properties[propertyName]) as P
    }

    fun trigger() {
        println("Triggering: ${rndrMachine.key}, properties:${properties}")
        rndrMachine.triggerMe(this)
    }

}