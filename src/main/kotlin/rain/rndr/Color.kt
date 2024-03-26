package rain.rndr

import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorRGBa
import rain.interfaces.*
import rain.language.*
import rain.patterns.*
import rain.utils.*

open class Color(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
):RndrMachine<CellBuilder>(key, properties, context) {

    override val label = LocalContext.getLabel("ColorMachine", "Machine", "Leaf") { k, p, c -> Color(k, p, c) }



    // TODO: able to not have to pass the 2nd type parameter here?
//    val h = relatedMachine<Value>("H")

    val h: Value get() = targetsOrMakeAs("H", primaryLabel, getProperty("h_key"))
    val s = relatedMachine<Value>("S")
    val v = relatedMachine<Value>("V")
    val a = relatedMachine<Value>("A")

    fun colorHSVa(act:Act<Color>) = ColorHSVa(
        act.targetActs["H"] as ValueAct as,
        s.value,
        v.value,
        a.value,
    )

    fun colorRGBa(act:Act<Color>): ColorRGBa = colorHSVa(act).toRGBa()

}
