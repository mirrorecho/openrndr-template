package rain.rndr

// TODO: OK To not include type parameter of the Act here????
class Trigger(
    val score: Score,
    val rndrMachine: RndrMachine<*>, // TODO: <*> OK here?
    val act:Act? = null, // passed if triggering an existing act /// TODO: rethink????
    val runningTime:Double = 0.0, // TODO: used?
    val properties: Map<String, Any?>
    // TODO... the trigger is what will need to connect an Act to its related/sub acts...
    //  ... (properties enough to implement through?)
) {

    // TODO: maybe remove this... in order to avoid the complexity of cascading triggers/acts?
    //  or, perhaps rethink... part of the issue is that these value acts ARE NOT associated with a machine...
    fun propertyAsValueAct(propertyName:String, actName:String?=null):ValueAct {
        return if (actName.isNullOrBlank()) ValueAct(value=propertyAs(propertyName)) else ValueAct(name=actName, value=propertyAs(propertyName))
    }

    // TODO: implement?
//    fun <RA:Act>relatedAct(relationshipName: String, actName: String?=null): RA {
//        return rndrMachine.getRelatedAct(relationshipName, actName)
//    }

    fun <RA:Act>triggerRelated(relationshipName: String, actName: String?=null, properties: Map<String, Any?>): RA {
        val rMachine = rndrMachine.targetsAs<RndrMachine<RA>>(relationshipName)
        val rTrigger = Trigger(
            this.score,
            rMachine,
            null,
            this.runningTime,
            properties
        )
        return rMachine.actFactory!!.invoke(rTrigger) // TODO maybe: !! OK here?
    }

    val dur: Double by this.properties

    fun <P>propertyAs(propertyName:String):P {
        return properties[propertyName] as P
    }

}