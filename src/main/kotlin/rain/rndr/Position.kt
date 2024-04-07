package rain.rndr

import org.openrndr.Program
import org.openrndr.math.Vector2
import rain.interfaces.ContextInterface
import rain.language.LocalContext
import rain.language.NodeCompanion
import rain.language.NodeLabel
import rain.language.Relationship
import rain.utils.*

open class Position(
    key:String = autoKey(),
):RndrMachine(key) {
    companion object : NodeCompanion<Position>(RndrMachine.childLabel { k -> Position(k) })
    override val label: NodeLabel<out Position> = Position.label

    val x = cachedTarget(Relationship.rndr.X, Value.label)
    val y = cachedTarget(Relationship.rndr.Y, Value.label)

    override val targetProperties = listOf(::x, ::y)

    // TODO: accommodate local storage
    fun vector(program: Program): Vector2 = Vector2(
        (x.target?.value ?: 0.5) * program.width,
        (y.target?.value ?: 0.5)  * program.height,
    )
}
