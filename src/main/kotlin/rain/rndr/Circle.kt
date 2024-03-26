package rain.rndr


import org.openrndr.Program
import rain.utils.*
import rain.interfaces.*
import rain.language.*
import  rain.machines.*
import rain.patterns.*


]
open class Circle(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
    ):RndrMachine<CircleCellBuilder>(key, properties, context) {

    override val label = LocalContext.getLabel("CircleMachine", "Machine", "Leaf") { k, p, c -> CircleMachine(k, p, c) }

    val radius = relatedMachine<Value>("RADIUS")
    val strokeColor = relatedMachine<Color>("STROKE_COLOR")

    fun render(score: Score, program: Program) {
//        println("circle with x position " + position.x.value.toString())
        program.apply {
            drawer.fill = fillColor?.colorRGBa()
            drawer.stroke = strokeColor.target.
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
