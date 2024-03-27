package rain.rndr

import org.openrndr.Program
import org.openrndr.math.Vector2
import rain.interfaces.ContextInterface
import rain.language.LocalContext
import rain.patterns.CellBuilder
import rain.utils.autoKey

open class Position(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
):RndrMachine<CellBuilder>(key, properties, context) {

    override val label = LocalContext.getLabel("Position", "Machine", "Leaf") { k, p, c -> Color(k, p, c) }

    // TODO: what was the idea of the below method?
//    fun setTargets(x:MachineFunc?, y:MachineFunc?) {
//        if (x == null) {
//            val xFunc = MachineFunc()
//            // TODO, should be specific type(label) of relationship for POSITION_X
//            Relationship(source_key = this.key, target_key = xFunc.key).createMe()
//        } else {}
//    }

    // TODO: accommodate local storage
    fun vector(program: Program): Vector2 = Vector2(
        x.value * program.width,
        y.value * program.height,
    )
}

fun positionMachine(key:String= autoKey(), single:Boolean=true,
                 xKey:String = "X",
                 yKey:String = "Y",
): RndrMachine<Position> {
    return createRndrMachine(key, single) { tr -> Position(tr) }.apply {
        relate("X", xKey); relate("Y", yKey);
    }
}