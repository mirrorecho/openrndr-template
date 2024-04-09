package rain.rndr.nodes

import rain.interfaces.*
import rain.language.*
import rain.rndr.relationships.*
import rain.utils.*

import org.openrndr.Program
import org.openrndr.math.Vector2


open class Position(
    key:String = autoKey(),
): RndrMachine(key) {
    companion object : NodeCompanion<Position>(RndrMachine.childLabel { k -> Position(k) })
    override val label: NodeLabel<out Position> = Position.label

    val x = cachedTarget(X, Value.label)
    val y = cachedTarget(Y, Value.label)

    override val targetProperties = listOf(::x, ::y)

    fun vector(program: Program): Vector2 = Vector2(
        (x.target?.value ?: 0.5) * program.width,
        (y.target?.value ?: 0.5)  * program.height,
    )
}
