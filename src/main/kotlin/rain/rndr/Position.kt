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

    override val label = LocalContext.getLabel("Position", "RndrMachine", "Machine", "Leaf") { k, p, c -> Color(k, p, c) }

    val x: Value = targetMachine("X", "x_key")
    val y: Value = targetMachine("Y", "y_key")

    // TODO: accommodate local storage
    fun vector(program: Program): Vector2 = Vector2(
        x.value * program.width,
        y.value * program.height,
    )
}
