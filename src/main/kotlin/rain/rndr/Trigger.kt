package rain.rndr

import rain.patterns.*
import rain.utils.*

// TODO maybe: consider renaming to Event? (wait to see how this is implemented in SuperCollider implementation)
// TODO: OK To not include type parameter of the Act here????
class Trigger<AT:Act>(
    val score: Score,
    val rndrMachine: RndrMachine<AT, *>,
    val runningTime:Double = 0.0, // TODO: used?
    val properties: Map<String, Any?> = mapOf()
    // TODO... the trigger is what will need to connect an Act to its related/sub acts...
    //  ... (properties enough to implement through?)
) {

    val actName: String = if (rndrMachine.single) { rndrMachine.key } else { properties["act"] as String? ?: autoKey()  }
    val act:AT = score.getAct(actName) ?: rndrMachine.makeAct(this)

//    // TODO: maybe remove this... in order to avoid the complexity of cascading triggers/acts?
//    //  or, perhaps rethink... part of the issue is that these value acts ARE NOT associated with a machine...
//    fun propertyAsValueAct(propertyName:String, actName:String?=null):Value {
//        return if (actName.isNullOrBlank()) Value(value=propertyAs(propertyName)) else Value(name=actName, value=propertyAs(propertyName))
//    }

    // TODO: implement?
//    fun <RA:Act>relatedAct(relationshipName: String, actName: String?=null): RA {
//        return rndrMachine.getRelatedAct(relationshipName, actName)
//    }

    // TODO: combine this up into the Trigger itself (the Trigger IS THE EVENT!)
    fun animateEvent(name:String): AnimateEvent? {
        properties[name]?.let {
            return AnimateEvent(
                propertyAs(name),
                propertyAs("$name:animate"),
                propertyAs("$name:easing"),
                propertyAs("$name:init"),
            )
        }
        return null
    }

    // TODO: rename so that it's clear that a trigger is being created here...
    fun <RA:Act>relatedAct(relationshipName: String, actName: String?=null, properties: Map<String, Any?> = mapOf() ): RA? {
        // TODO: pass-through act names
        rndrMachine.targetsAs<RndrMachine<RA, *>?>(relationshipName)?.let {
            return it.createTrigger(score, this.runningTime, properties).act
        }
        return null
    }

    val dur: Double by this.properties

    val durMs get() = (dur * 1000.0).toLong()

    fun <P>propertyAs(propertyName:String):P {
        return (properties[propertyName] ?: rndrMachine.properties[propertyName]) as P
    }

//    fun setAct() {
//        act = score.getAct(actName) ?: rndrMachine.actFactory?.invoke(this)
//        act?.let { score.addAct(it) }
//        // if (rndrMachine.actFactory == null) println("NO ACT FACTORY FOR " + rndrMachine.key)
//    }

    fun trigger() {
        println("Triggering: ${rndrMachine.key}, act:${act?.name}, properties:${properties}")
        act?.triggerMe(this)
    }

}