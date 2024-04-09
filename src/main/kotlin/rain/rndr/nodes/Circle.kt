package rain.rndr.nodes

import rain.interfaces.*
import rain.language.*
import rain.rndr.relationships.*
import rain.utils.*

import org.openrndr.Program

open class Circle(
    key:String = autoKey(),
    ): RndrMachine(key) {
    companion object : NodeCompanion<Circle>(RndrMachine.childLabel { k -> Circle(k) })
    override val label: NodeLabel<out Circle> = Circle.label

    var radius = cachedTarget(RADIUS, Value.label)
    var strokeWeight = cachedTarget(STROKE_WEIGHT, Value.label)
    var strokeColor = cachedTarget(STROKE_COLOR, Color.label)
    val fillColor = cachedTarget(FILL_COLOR, Color.label)
    val position = cachedTarget(POSITION, Position.label)
//
    override val targetProperties = listOf(::radius, ::strokeWeight, ::strokeColor, ::fillColor, ::position)


//    // TODO: implement if needed (or remove)
//    override fun trigger(event:Event) {
//        // TODO: implement
//        super.trigger(event)
//    }

    fun render(program: Program) {
//        println("circle with x position " + position.x.value.toString())
        program.apply {
            drawer.fill = fillColor.target?.colorRGBa()
            drawer.stroke = strokeColor.target?.colorRGBa()
            strokeWeight.target?.apply { drawer.strokeWeight = value }
            drawer.circle(
                position.target!!.vector(program), // NOTE: ERROR IF NO POSITION
                radius.target?.value ?: 90.0,
            )
        }
    }
}
