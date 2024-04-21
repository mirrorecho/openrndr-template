package rain.rndr.nodes

import rain.interfaces.*
import rain.language.*
import rain.rndr.relationships.*
import rain.utils.*

import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorRGBa

open class Color(
    key:String = autoKey(),
): RndrMachine(key) {
    companion object : NodeLabel<Color>(Color::class, RndrMachine, { k -> Color(k) })
    override val label: NodeLabel<out Color> = Color

    val h = cachedTarget(H, Value)
    val s = cachedTarget(S, Value)
    val v = cachedTarget(V, Value)
    val a = cachedTarget(A, Value)

    override val targetProperties = listOf(::h, ::s, ::v, ::a)

    fun colorHSVa() = ColorHSVa(
        h.target?.value ?: 90.0,
        s.target?.value ?: 0.8,
        v.target?.value ?: 0.8,
        a.target?.value ?: 0.6,
    )

    fun colorRGBa(): ColorRGBa = colorHSVa().toRGBa()

}
