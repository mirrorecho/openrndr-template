package rain.rndr

import org.openrndr.math.Vector2
import rain.interfaces.ContextInterface
import rain.language.LocalContext
import rain.language.Relationship

open class Position(
    name:String = rain.utils.autoKey(),
    var x: ValueAct = ValueAct(),
    var y: ValueAct = ValueAct(),
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
    fun vector(): Vector2 = Vector2(
        x.value,
        y.value,
    )
}