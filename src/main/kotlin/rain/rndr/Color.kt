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

    val h: Value = targetMachine("H", "h_key")
    val s: Value = targetMachine("S", "s_key")
    val v: Value = targetMachine("V", "v_key")
    val a: Value = targetMachine("A", "a_key")

    fun colorHSVa() = ColorHSVa(
        h.value,
        s.value,
        v.value,
        a.value,
    )

    fun colorRGBa(): ColorRGBa = colorHSVa().toRGBa()

}
