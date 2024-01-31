package rain.rndr

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.openrndr.Program
import org.openrndr.application
import org.openrndr.launch
import rain.language.Palette
import rain.patterns.CellPattern
import rain.utils.autoKey
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class Score(
    val machinePalette: Palette<RndrMachine<Act>>,
//    val program: Program
) {

    // TODO... used now with new trigger model?
    // TODO maybe: should the key be a pair of the machone node key PLUS the act name? (as opposed to just the act name?)
    private val acts: MutableMap<String, Act> = mutableMapOf()

    private val timeCodes: MutableMap<Double, MutableList<Trigger>> = mutableMapOf()

//    fun getAct(machineKey: String, actName: String? = null) {
//
//    }

//    fun updateOrCreateAct(machineFunc: MachineFunc, actName: String): Act{ //TODO maybe: machine better here than machineName?
//        val act = actions.getOrPut(actName) {
//            Act(
//                actName,
//                machineFunc,
//                machineFunc.properties.toMutableMap(),
//                this
//                // TODO: implement map of machine relationships to acts
//            )
//        }
//        return act
//    }

    private fun createTrigger(time:Double, properties: Map<String, Any?>) {
        val triggerList = timeCodes.getOrPut(time) {mutableListOf()}

        //TODO: accommodate triggering existing act (updating it)
        val machine = machinePalette[properties["machine"] as String] as RndrMachine<Act>
        val trigger = Trigger(this, machine, null, time, properties)

        // TODO: consider creating act here upfront (as opposed to during animation)
        triggerList.add(trigger)
    }

    // TODO: test and document ... also, should this be the public method, or should it be something else?
    fun createTriggers(pattern: CellPattern, runningTime:Double=0.0): Double {

        // TODO: refactor and simplify this logic...? (also look at old python code)
        var patternDur = 0.0
        println(pattern)
        if (pattern.isLeaf) {
            // TODO maybe: handle fancy logic like hooks here?
            pattern.veins.forEach {
                val veinDur = it["dur"] as Double
                if (pattern.simultaneous) {
                    createTrigger(runningTime, it)
                    if (veinDur > patternDur) patternDur = veinDur
                } else {
                    createTrigger(runningTime+patternDur, it)
                    patternDur += veinDur
                }
            }
        } else {
            pattern.branches.asTypedSequence<CellPattern>().forEach { branch ->
                println("yo branch " + branch.toString())
                if (pattern.simultaneous) {
                    val branchEndTime = createTriggers(branch, runningTime)
                    val branchDur = branchEndTime-runningTime
                    if (branchDur > patternDur) patternDur = branchDur
                } else {
                    val branchEndTime = createTriggers(branch, runningTime+patternDur)
                    patternDur = branchEndTime-runningTime
                }
            }
        }
        return runningTime + patternDur
    }

    fun reset() {
        acts.clear()
        timeCodes.clear()
    }

    fun play() = application {
        reset()
        var prevTriggerTime = 0.0
//        println(timeCodes.toSortedMap())

        program {

            launch {
                timeCodes.keys.sorted().forEach { triggerTime ->
                    val triggerList = timeCodes[triggerTime]!!
                    val delayTime = triggerTime - prevTriggerTime
                    if (delayTime > 0) delay((delayTime).toDuration(DurationUnit.SECONDS))
                    launch {
                        triggerList.forEach { tr: Trigger ->

                            tr.rndrMachine.actFactory?.invoke(tr)?.let { act ->
                                acts[act.name] = act
                                act.isRunning = true
                                println("starting: " + act.toString())
                            }

//                            if (action is Act) {
//                                action.start()
//                                // println("starting: " + action.toString())
//                            }
//                            // TODO: implement Update actions here (inc. stopping at appropriate dur)
////                            else {
////                                if (p["gate"] != true) launch {
////                                    // TODO: consider accommodating ops with indeterminate durs...
////                                    delay((p["dur"] as Double).toDuration(DurationUnit.SECONDS))
////                                    machine.stopOp(op)
////                                }
////                            }
                        }
                    }
                    prevTriggerTime = triggerTime
                }
            }

            extend {

                // TODO: need to worry about acts ordering?
                acts.forEach { act ->

                }

                // DO NOTHING?

//                animation.updateAnimation()
//                if (!animation.hasAnimations()) {
//                    animation.apply {
//                        ::x.animate(width.toDouble(), 1000, Easing.CubicInOut)
//                        ::x.complete()
//                        ::x.animate(0.0, 1000, Easing.CubicInOut)
//                        ::x.complete()
//                    }
//                }
//                drawer.fill = myColor
//                drawer.stroke = null
//                drawer.circle(animation.x, height / 2.0, 100.0)
            }

        }
    }

}