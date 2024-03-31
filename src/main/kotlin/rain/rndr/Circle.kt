package rain.rndr

import org.openrndr.Program
import rain.utils.*
import rain.interfaces.*
import rain.language.*
import  rain.machines.*
import rain.patterns.*

open class Circle(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
    ):RndrMachine<CircleCellBuilder>(key, properties, context) {

    override val label = LocalContext.getLabel("Circle", "RndrMachine", "Machine", "Leaf") { k, p, c -> Circle(k, p, c) }

    val radius = targetMachine<Value>("RADIUS", "radiusKey")
    val strokeWeight = targetMachine<Value>("STROKE_WEIGHT", "strokeWeightKey")
    val strokeColor = targetMachine<Color>("STROKE_COLOR", "strokeColorKey")
    val fillColor = targetMachine<Color>("FILL_COLOR", "fillColorKey")
    val position = targetMachine<Position>("POSITION", "positionKey")

    // TODO: implement if needed (or remove)
    override fun trigger(event:Event) {
        // TODO: implement
        super.trigger(event)
    }

    fun render(score: Score, program: Program) {
//        println("circle with x position " + position.x.value.toString())
        program.apply {
            drawer.fill = fillColor?.colorRGBa()
            drawer.stroke = strokeColor?.colorRGBa()
            strokeWeight?.apply { drawer.strokeWeight = value }
            drawer.circle(
                position.vector(program),
                radius.value,
            )
        }
    }
}



class CircleCellBuilder(cell:Cell):CellBuilder(cell) {
    fun radius() {

    }
}
