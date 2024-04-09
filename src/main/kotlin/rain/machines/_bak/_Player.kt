package rain.machines._bak

//
//import kotlinx.coroutines.*
//import rain.language.*
//import rain.patterns.*
//import kotlin.time.DurationUnit
//import kotlin.time.toDuration
//
//
//
//// TODO: implement tempos
//class _Player(
//    val cellPattern: CellPattern,
//    val machinePalette: Palette<Machine>,
//) {
//
//    // TODO maybe: instead of just a list, this could be something like a timed trigger bundle? (inc. max dur predefined?)
//    private val triggers: MutableMap<Double, MutableList<Map<String, Any?>>> = mutableMapOf()
//
//    private fun setTrigger(time:Double, properties: Map<String, Any?>) {
//        val timeTriggerList = triggers.getOrPut(time) {mutableListOf()}
//        timeTriggerList.add(properties)
//    }
//
//    // TODO: test and document
//    private fun setTriggers(pattern:CellPattern, startTime:Double=0.0): Double {
//        var runningTime = startTime
//        // TODO: refactor and simplify this logic...? (also look at old python code)
//        var patternDur = 0.0
//        if (pattern.isLeaf) {
//            // TODO maybe: handle fancy logic like hooks here?
//            pattern.veins.forEach {
//                val veinDur = it["dur"] as Double
//                if (pattern.simultaneous) {
//                    setTrigger(runningTime, it)
//                    if (veinDur > patternDur) patternDur = veinDur
//                } else {
//                    setTrigger(runningTime+patternDur, it)
//                    patternDur += veinDur
//                }
//            }
//        } else {
//            pattern.branches.asTypedSequence<CellPattern>().forEach { branch ->
//                if (pattern.simultaneous) {
//                    val branchEndTime = setTriggers(branch, runningTime)
//                    val branchDur = branchEndTime-runningTime
//                    if (branchDur > patternDur) patternDur = branchDur
//                } else {
//                    val branchEndTime = setTriggers(branch, runningTime+patternDur)
//                    patternDur = branchEndTime-runningTime
//                }
//            }
//        }
//        return runningTime + patternDur
//    }
//
//    fun reset() {
//        triggers.clear()
//    }
//
//    fun play() = runBlocking {
//        reset()
//        // TODO maybe - auto-populate machinePalette?
//        // TODO maybe - don't set all triggers upfront? ... maybe just launch co-routines ..as needed?
//        setTriggers(this@_Player.cellPattern)
//        var prevTriggerTime = 0.0
//        println(triggers.toSortedMap())
//
//        triggers.keys.sorted().forEach { triggerTime ->
//            val triggerList = triggers[triggerTime]!!
//            val delayTime = triggerTime - prevTriggerTime
//            if (delayTime > 0) delay((delayTime).toDuration(DurationUnit.SECONDS))
//            launch { triggerAt(triggerTime, triggerList) }
//            prevTriggerTime = triggerTime
//        }
//
//    }
//
//    fun triggerAt(runningTime: Double, triggerList: MutableList<Map<String, Any?>>) {
//        triggerList.forEach { p ->
//            this@_Player.machinePalette[p["machine"] as String]?.trigger(runningTime, p)
//        }
//
//    }
//}