package rain.rndr

import org.openrndr.Program
import org.openrndr.math.Vector2
import rain.utils.autoKey

open class Position(
    name:String = rain.utils.autoKey(),
    var x: Value = Value(),
    var y: Value = Value(),
): Act(name) {

    // TODO: what was the idea of the below method?
//    fun setTargets(x:MachineFunc?, y:MachineFunc?) {
//        if (x == null) {
//            val xFunc = MachineFunc()
//            // TODO, should be specific type(label) of relationship for POSITION_X
//            Relationship(source_key = this.key, target_key = xFunc.key).createMe()
//        } else {}
//    }

    // TODO: accommodate local storage
    fun vector(program: Program): Vector2 = Vector2(
        x.value * program.width,
        y.value * program.height,
    )
}

fun positionMachine(key:String= autoKey(), single:Boolean=true,
                 xKey:String = "X",
                 yKey:String = "Y",
): RndrMachine<Position> {
    return createRndrMachine(key, single) { tr ->
        // TODO: determine defaults?
        Position(
            name = tr.actName,
            x = tr.relatedAct("X"), // NOTE: this is set at the machine level above
            y = tr.relatedAct("Y", properties = mapOf("value" to 0.5)),
        )
    }.apply { relate("X", xKey); relate("Y", yKey); }
}