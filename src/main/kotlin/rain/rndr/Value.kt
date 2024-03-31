package rain.rndr

import org.openrndr.animatable.Animatable
import rain.interfaces.ContextInterface
import rain.language.LocalContext
import rain.patterns.CellBuilder
import rain.patterns.Event
import rain.patterns.ValueEvent
import rain.utils.autoKey
import kotlin.math.absoluteValue

class AnimationValue(
    var value: Double
):Animatable()

open class Value(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
    ):RndrMachine<CellBuilder>(key, properties, context) {

    override val label = LocalContext.getLabel("RndrMachine", "Machine", "Leaf") { k, p, c -> Value(k, p, c) }

    var animationValue = AnimationValue(0.0)

    var value get() = animationValue.value
        set(v) {animationValue.value=v}

    // TODO: OK that this isn't an override (due to more specific event type?)
    fun trigger(event: ValueEvent) {
        super.trigger(event)

        value?.let {v ->
            if (event.isAnimation) {
                println("ANIM event ${event.key}: ---------------------- ")
                println("value: ${event.value}")
                println("animateDur: ${event.animateDur}")
                println("easing: ${event.easing}")
                println("initValue: ${event.initValue}")
                event.initValue?.let { value = it }

                animationValue.apply {
                    if (event.animateDurMs >= 0) {
                        ::value.animate(v, event.animateDurMs, event.easingTyped)
                        ::value.complete()
                    } else {
                        // TODO, a better way to keep current value for the duration instead of "animating" it?
                        ::value.animate(value, event.durMs + event.animateDurMs)
                        ::value.complete()
                        ::value.animate(v, event.animateDurMs.absoluteValue, event.easingTyped)
                        ::value.complete()
                    }
                }
            } else {
                value = v
                println("STATIC VALUE ${event.key}: ${event.value}")
            }
        }


    }

}



//fun createValues(single:Boolean=true, vararg keys: String) {
//    keys.forEach { k ->
//        createRndrMachine(k, single) { Value(it) }
//    }
//}