package rain.rndr


import org.openrndr.Program
import rain.utils.*
import rain.interfaces.*
import rain.language.*
import  rain.machines.*
import rain.patterns.*


]
open class CircleMachine(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
    ):RndrMachine<Circle, CircleCellBuilder>(key, properties, context) {

    override val label = LocalContext.getLabel("CircleMachine", "Machine", "Leaf") { k, p, c -> CircleMachine(k, p, c) }

    val radius = relatedMachine<ValueMachine, Value>("RADIUS")
    val strokeColor = relatedMachine<ValueMachine, Value>("STROKE_COLOR")

    override fun makeAct(tr: Trigger<Circle>): Circle {
        return Circle(tr, this)
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

    }

open class Circle(
    trigger:Trigger<Circle>,
    override val machine: CircleMachine,
    val radius: Value = machine.radius.relatedAct(trigger),
    ): Act(trigger) {

//    val position: Position = trigger.relatedAct("POSITION", positionName)!!
//    val strokeColor: Color? = trigger.relatedAct("STROKE_COLOR", strokeColorName)
//    val strokeWeight: Value? = trigger.relatedAct("STROKE_WEIGHT", strokeWeightName)
//    val fillColor: Color? = trigger.relatedAct("FILL_COLOR", fillColorName)

    // TODO MAYBE: a base drawing class with standard attributes like color, position, etc.

    override fun render(score: Score, program: Program) {
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


fun circleMachine(
    key:String= autoKey(),
    single:Boolean = true,
// TODO: consider some similar implementation as below to be able to specify related machine keys
    radiusKey: String = "RADIUS",
    positionKey: String = "POSITION",
    strokeColorKey: String = "STROKE_COLOR",
    strokeWeightKey: String = "STROKE_WEIGHT",
    fillColorKey: String = "FILL_COLOR",
): RndrMachine<Circle> {

    return createRndrMachine(key, single) { tr -> Circle(tr)
    }.apply {
        relate("RADIUS", radiusKey)
        relate("POSITION", positionKey)
        relate("STROKE_COLOR", strokeColorKey)
        relate("STROKE_WEIGHT", strokeWeightKey)
        relate("FILL_COLOR", fillColorKey)
    }
}
