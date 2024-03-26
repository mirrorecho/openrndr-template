package rain.rndr

import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorRGBa
import rain.interfaces.*
import rain.language.*
import rain.patterns.*
import rain.utils.*

open class ColorMachine(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
):RndrMachine<Color, CellBuilder>(key, properties, context) {

    override val label = LocalContext.getLabel("ColorMachine", "Machine", "Leaf") { k, p, c -> ColorMachine(k, p, c) }

    // TODO: able to not have to pass the 2nd type parameter here?
    val h = relatedMachine<ValueMachine, Value>("H")
    val s = relatedMachine<ValueMachine, Value>("S")
    val v = relatedMachine<ValueMachine, Value>("V")
    val a = relatedMachine<ValueMachine, Value>("A")

    fun colorHSVa() = ColorHSVa(
        h.value,
        s.value,
        v.value,
        a.value,
    )

    override fun makeAct(tr: Trigger<Color>): Color {
        return Color(tr, this)
    }
}

open class Color(
    trigger: Trigger<*>,
    override val machine: ColorMachine,
    val h:Value = machine.h.relatedAct(trigger),
    val s:Value = machine.s.relatedAct(trigger),
    val v:Value = machine.v.relatedAct(trigger),
    val a:Value = machine.a.relatedAct(trigger),
): Act(trigger) {

    fun colorHSVa() = ColorHSVa(
        h.value,
        s.value,
        v.value,
        a.value,
    )

    fun colorRGBa(): ColorRGBa = colorHSVa().toRGBa()

}

//fun colorMachine(key:String= autoKey(), single:Boolean=true,
//                 hKey:String = "H",
//                 sKey:String = "S",
//                 vKey:String = "V",
//                 aKey:String = "A",
//): RndrMachine<Color> {
//    return createRndrMachine(key, single) { tr -> Color(tr) }.apply {
//        relate("H", hKey)
//        relate("S", sKey)
//        relate("V", vKey)
//        relate("A", aKey)
//    }
//}