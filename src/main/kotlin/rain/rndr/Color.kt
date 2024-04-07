package rain.rndr

import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorRGBa
import rain.interfaces.*
import rain.language.*
import rain.patterns.*
import rain.utils.*

open class Color(
    key:String = autoKey(),
):RndrMachine(key) {
    companion object : NodeCompanion<Color>(RndrMachine.childLabel { k -> Color(k) })
    override val label: NodeLabel<out Color> = Color.label

    val h = cachedTarget(Relationship.rndr.H, Value.label)
    val s = cachedTarget(Relationship.rndr.S, Value.label)
    val v = cachedTarget(Relationship.rndr.V, Value.label)
    val a = cachedTarget(Relationship.rndr.A, Value.label)

    override val targetProperties = listOf(::h, ::s, ::v, ::a)

    fun colorHSVa() = ColorHSVa(
        h.target?.value ?: 90.0,
        s.target?.value ?: 0.8,
        v.target?.value ?: 0.8,
        a.target?.value ?: 0.6,
    )

    fun colorRGBa(): ColorRGBa = colorHSVa().toRGBa()

}
