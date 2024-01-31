package rain.rndr


import org.openrndr.Program
import rain.interfaces.ContextInterface
import rain.language.LocalContext



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



    // TODO: accommodate local storage...
    //  ... point to objects that could EITHER represent
    //  - machine nodes
    //  - OR simple values (from this node's properties)
    //  - OR collections of values (from this node's properties)
    // TODO: or maybe by lazy is not ideal here? think about it...

    override fun render(score: Score, program: Program) {
        program.apply {
            drawer.stroke = strokeColor.colorRGBa()
            drawer.strokeWeight = strokeWeight.value
            drawer.fill = fillColor?.colorRGBa()
            drawer.circle(
                position.vector(),
                radius.value
            )
        }
    }

}