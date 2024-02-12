package rain.rndr


import org.openrndr.Program
import rain.utils.autoKey


open class Circle(
    name:String = rain.utils.autoKey(),

    var radius: ValueAct = ValueAct(),
    var position: Position = Position(),
    var strokeColor: Color = Color(),
    var strokeWeight: ValueAct = ValueAct(),
    var fillColor: Color? = null,

): Act(name) {

    // TODO MAYBE: a base drawing class with standard attributes like color, position, etc.

    // TODO... how can this factory work given the val parameters above??!!
//    override val label = LocalContext.getLabel("Circle", "RndrMachine", "MachineFunc", "Machine", "Leaf") { k, p, c -> Circle(k, p, c) }


    // TODO: would be slick to implement something like below to avoid boilerplate
//    val relatedActs = mapOf(
//        "RADIUS" to ::radius,
//        "POSITION" to ::position,
//    )

    // TODO: accommodate local storage...
    //  ... point to objects that could EITHER represent
    //  - machine nodes
    //  - OR simple values (from this node's properties)
    //  - OR collections of values (from this node's properties)
    // TODO: or maybe by lazy is not ideal here? think about it...

    override fun trigger(properties: Map<String, Any?>) {
        super.trigger(properties)
        triggerValue("radius", radius, properties)

//        val fo = relatedActs["RADIUS"]
//        fo?.invoke()?.trigger(properties)
    }

    override fun render(score: Score, program: Program) {
//        println("circle with x position " + position.x.value.toString())
        program.apply {
            drawer.fill = fillColor?.colorRGBa()
            drawer.stroke = strokeColor?.colorRGBa()
            drawer.circle(
                position.vector(program),
                radius.value,
            )
//            drawer.stroke = strokeColor.colorRGBa()
//            drawer.strokeWeight = strokeWeight.value
//            drawer.fill = fillColor?.colorRGBa()
//            drawer.circle(
//                position.vector(),
//                radius.value
//            )
        }
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

    return createRndrMachine(key, single) { tr ->
        Circle(
            name = tr.actName,
            // TODO: simplify this argument assignment below
            radius = tr.relatedAct("RADIUS",
                properties = mapOf(
                    "value" to tr.properties["radius"],
                    "animate" to tr.properties["radius:animate"]
                ),
            ),
            position = tr.relatedAct("POSITION"),
            strokeColor = tr.relatedAct("STROKE_COLOR"),
            strokeWeight = tr.relatedAct("STROKE_WEIGHT", properties = mapOf("value" to 1.0)),
            fillColor = tr.relatedAct("FILL_COLOR"),
        )
    }.apply {
        relate("RADIUS", radiusKey)
        relate("POSITION", positionKey)
        relate("STROKE_COLOR", strokeColorKey)
        relate("STROKE_WEIGHT", strokeWeightKey)
        relate("FILL_COLOR", fillColorKey)
    }
}
