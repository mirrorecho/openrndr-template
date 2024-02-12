package rain.rndr

import rain.utils.autoKey

// TODO maybe: consider renaming to Event? (wait to see how this is implemented in SuperCollider implementation)
// TODO: OK To not include type parameter of the Act here????
class Trigger(
    val score: Score,
    val rndrMachine: RndrMachine<*>, // TODO: <*> OK here?
    val runningTime:Double = 0.0, // TODO: used?
    val properties: Map<String, Any?> = mapOf()
    // TODO... the trigger is what will need to connect an Act to its related/sub acts...
    //  ... (properties enough to implement through?)
) {

    val actName: String = if (rndrMachine.single) { rndrMachine.key } else { properties["act"] as String? ?: autoKey()  }
    var act:Act? = null

    // TODO: maybe remove this... in order to avoid the complexity of cascading triggers/acts?
    //  or, perhaps rethink... part of the issue is that these value acts ARE NOT associated with a machine...
    fun propertyAsValueAct(propertyName:String, actName:String?=null):ValueAct {
        return if (actName.isNullOrBlank()) ValueAct(value=propertyAs(propertyName)) else ValueAct(name=actName, value=propertyAs(propertyName))
    }

    // TODO: implement?
//    fun <RA:Act>relatedAct(relationshipName: String, actName: String?=null): RA {
//        return rndrMachine.getRelatedAct(relationshipName, actName)
//    }

    fun <RA:Act>relatedAct(relationshipName: String, actName: String?=null, properties: Map<String, Any?> = mapOf() ): RA {
        val rMachine = rndrMachine.targetsAs<RndrMachine<RA>>(relationshipName)
        val rTrigger = score.createTrigger(
            this.runningTime,
            properties,
            rMachine
        )

//        val rTrigger = Trigger(
//            this.score,
//            rMachine,
//            null,
//            this.runningTime,
//            properties
//        )
//        val act = rMachine.actFactory!!.invoke(rTrigger) // TODO maybe: !! OK here?
//        score.addAct(act)

        //TODO:  type cast OK here??
        return rTrigger.act as RA
    }

    val dur: Double by this.properties

    fun <P>propertyAs(propertyName:String):P {
        return (properties[propertyName] ?: rndrMachine.properties[propertyName]) as P
    }

    fun setAct() {
        act = score.getAct(actName) ?: rndrMachine.actFactory?.invoke(this)
        act?.let { score.addAct(it) }
        // if (rndrMachine.actFactory == null) println("NO ACT FACTORY FOR " + rndrMachine.key)
    }

    fun trigger() {
        println("Triggering: " + rndrMachine.key + ", act: " + (act?.name ?: ""))
        act?.trigger(properties)
    }

}