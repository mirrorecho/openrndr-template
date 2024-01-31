package rain.rndr

import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorRGBa
import rain.interfaces.ContextInterface
import rain.language.LocalContext

open class Color(
    name:String = rain.utils.autoKey(),
    var h: ValueAct = ValueAct(),
    var s: ValueAct = ValueAct(),
    var v: ValueAct = ValueAct(),
    var a: ValueAct = ValueAct(),
): Act(name) {

    fun colorHSVa() = ColorHSVa(
        h.value,
        s.value,
        v.value,
        a.value,
    )

    fun colorRGBa(): ColorRGBa = colorHSVa().toRGBa()


}