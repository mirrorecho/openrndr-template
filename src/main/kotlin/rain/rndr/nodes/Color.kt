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
    companion object : NodeCompanion<Color>(RndrMachine.childLabel { k -> Color(k) })
    override val label: NodeLabel<out Color> = Color.label

    val h = cachedTarget(H, Value.label)
    val s = cachedTarget(S, Value.label)
    val v = cachedTarget(V, Value.label)
    val a = cachedTarget(A, Value.label)

    override val targetProperties = listOf(::h, ::s, ::v, ::a)

    fun colorHSVa() = ColorHSVa(
        h.target?.value ?: 90.0,
        s.target?.value ?: 0.8,
        v.target?.value ?: 0.8,
        a.target?.value ?: 0.6,
    )

    fun colorRGBa(): ColorRGBa = colorHSVa().toRGBa()

}
