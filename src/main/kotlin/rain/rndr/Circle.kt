package rain.rndr

import org.openrndr.Program
import rain.utils.*
import rain.interfaces.*
import rain.language.*
import  rain.machines.*
import rain.patterns.*
import kotlin.reflect.KMutableProperty0

open class Circle(
    key:String = autoKey(),
    ):RndrMachine(key) {
    companion object : NodeCompanion<Circle>(RndrMachine.childLabel { k -> Circle(k) })
    override val label: NodeLabel<out Circle> = Circle.label

    var radius = cachedTarget(Relationship.rndr.RADIUS, Value.label)
    var strokeWeight = cachedTarget(Relationship.rndr.STROKE_WEIGHT, Value.label)
    var strokeColor = cachedTarget(Relationship.rndr.STROKE_COLOR, Color.label)
//    val fillColor: Color = targetMachine("FILL_COLOR", "fillColorKey")
//    val position: Position = targetMachine("POSITION", "positionKey")

    override val autoCreateTargets = listOf(::radius, ::strokeWeight, ::strokeColor)


//    // TODO: implement if needed (or remove)
//    override fun trigger(event:Event) {
//        // TODO: implement
//        super.trigger(event)
//    }

//    fun render(program: Program) {
////        println("circle with x position " + position.x.value.toString())
//        program.apply {
//            drawer.fill = fillColor.target?.colorRGBa()
//            drawer.stroke = strokeColor.target?.colorRGBa()
//            strokeWeight.target?.apply { drawer.strokeWeight = value }
//            drawer.circle(
//                position.vector(program),
//                radius.value,
//            )
//        }
//    }
}
