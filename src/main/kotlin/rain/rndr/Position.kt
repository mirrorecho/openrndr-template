package rain.rndr

import org.openrndr.Program
import org.openrndr.math.Vector2
import rain.utils.autoKey

open class Position(
    trigger:Trigger<*>,
    xName: String? = null,
    yName: String? = null
): Act(trigger) {

    val x:Value = trigger.relatedAct("X", xName)!!
    val y:Value = trigger.relatedAct("Y", yName)!!

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
    return createRndrMachine(key, single) { tr -> Position(tr) }.apply {
        relate("X", xKey); relate("Y", yKey);
    }
}