package rain.rndr

import org.openrndr.animatable.easing.Easing
import rain.utils.autoKey
import kotlin.math.absoluteValue

open class Value(
    name: String = autoKey(),
    var value: Double = 0.0,
): Act(name) {

    // TODO maybe: combine with animate() method below?
    override fun triggerMe(trigger: Trigger) {
        super.triggerMe(trigger)

        // TODO: confirm, is this the right "name" here?
        trigger.animateEvent(name)?.let {anim ->
            println("ANIM ---------------------- ")
            println("value: ${anim.value}")
            println("dur: ${anim.dur}")
            println("easing: ${anim.easing}")
            println("initValue: ${anim.initValue}")
            if (anim.isAnimation) {
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
            }
        }


    }

}