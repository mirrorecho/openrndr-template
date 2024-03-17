package rain.rndr

import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorRGBa
import rain.utils.autoKey

open class Color(
    name:String = rain.utils.autoKey(),
    var h: Value = Value(),
    var s: Value = Value(),
    var v: Value = Value(),
    var a: Value = Value(),
): Act(name) {

    fun colorHSVa() = ColorHSVa(
        h.value,
        s.value,
        v.value,
        a.value,
    )

    fun colorRGBa(): ColorRGBa = colorHSVa().toRGBa()


}

fun colorMachine(key:String= autoKey(), single:Boolean=true,
                 hKey:String = "H",
                 sKey:String = "S",
                 vKey:String = "V",
                 aKey:String = "A",
): RndrMachine<Color> {
    return createRndrMachine(key, single) { tr ->
        // TODO: consider other defaults?
        Color(
            name = tr.actName,
            h = tr.relatedAct("H", properties = mapOf("value" to 199.0)),
            s = tr.relatedAct("S", properties = mapOf("value" to 1.0)),
            v = tr.relatedAct("V", properties = mapOf("value" to 0.5)),
            a = tr.relatedAct("A", properties = mapOf("value" to 1.0)),
        )
    }.apply {
        relate("H", hKey)
        relate("S", sKey)
        relate("V", vKey)
        relate("A", aKey)
    }
}