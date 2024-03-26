package rain.rndr

import org.openrndr.animatable.easing.Easing
import rain.interfaces.ContextInterface
import rain.language.LocalContext
import rain.patterns.CellBuilder
import rain.utils.autoKey
import kotlin.math.absoluteValue

open class Value(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
    ):RndrMachine<CellBuilder>(key, properties, context) {

    override val label = LocalContext.getLabel("RndrMachine", "Machine", "Leaf") { k, p, c -> Value(k, p, c) }

    var value: Double = 0.0

}

open class ValueAct(
    trigger:Trigger<*>,

): Act(trigger) {

    // TODO maybe: combine with animate() method below?
    override fun triggerMe(trigger: Trigger<*>) {
        super.triggerMe(trigger)

        // TODO: confirm, is this the right "name" here?
        //  ALSO: this impletation will get confusing with different kindes of relationships, acts, etc.
        trigger.animateEvent(name)?.let {anim ->
            if (anim.isAnimation) {
                println("ANIM $name: ---------------------- ")
                println("value: ${anim.value}")
                println("dur: ${anim.dur}")
                println("easing: ${anim.easing}")
                println("initValue: ${anim.initValue}")
                anim.initValue?.let { value = it }

                // TODO: document this logic ...
                val animMs = if (anim.durMs == (0).toLong() || anim.durMs.absoluteValue > trigger.durMs) trigger.durMs else anim.durMs

                if (animMs >= 0) {
                    ::value.animate(anim.value, animMs, anim.easingTyped)
                    ::value.complete()
                } else {
                    // TODO, a better way to keep current value for the duration instead of "animating" it?
                    ::value.animate(value, trigger.durMs+animMs)
                    ::value.complete()
                    ::value.animate(anim.value, animMs.absoluteValue, anim.easingTyped)
                    ::value.complete()
                }

            } else {
                value = anim.value
                println("STATIC VALUE $name: $value")
            }
        }


    }

}


//fun createValues(single:Boolean=true, vararg keys: String) {
//    keys.forEach { k ->
//        createRndrMachine(k, single) { Value(it) }
//    }
//}