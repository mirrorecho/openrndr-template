package rain.patterns._bak

import rain.language.*
import rain.rndr.nodes.RndrMachine
import rain.patterns.relationships.*
import rain.utils.*


// =================================================================================================================

// TODO maybe: type parameter for the kind of machine that the Event triggers? (assume no: KISS!)
// TODO: for both this and ValueEvent, determine what should use getUp/propertiesUp vs simpler by this.properties
//open class Event(
//    key:String = autoKey(),
//): EventPattern, Leaf(key) {
//    companion object : NodeCompanion<Event>(Leaf.childLabel { k -> Event(k) })
//    override val label: NodeLabel<out Event> = Event.label
//
//    override val isAlter = false
//
//    override var dur: Double? by this.properties
//    override var simultaneous: Boolean by this.properties.apply { putIfAbsent("simultaneous", false) }
//
//    val triggersMachine = cachedTarget(TRIGGERS, RndrMachine.label)
//
//
//    fun relateMachine() {
//
////        machinePath?.forEach {  }
////
////        machineKey.let {mk->
////            if (gate==true) {
////                machine?.let { tl ->
////                    (mk?: autoKey()).also { mkn->
////                        // NOTE: not passing along even properties here just yet... they will be supplied at the time of triggering
////                        tl.merge(mkn)
////                    }
////                }
////            } else mk?.let {mko->
////                this.machinePath?.split("->")?.let {
////                    var select = Node(mko).selectSelf
////                    it.forEach { r->
////                        select = select.r(SelectDirection.RIGHT, r).n()
////                    }
////                    select.first?.key
////                }
////            }
////        }?.also {
////            this.relate("TRIGGERS", it)
////        } ?: run { println("WARNING: no machine to relate trigger to for event $key, machine $machine, machineKey $machineKey, machinePath $machinePath, gate $gate") }
//
//    }
//
//    // CONSIDER THIS:
////    operator fun invoke(key:String):Vein {
////        return vein(key)
////    }
//}

// =================================================================================================================

// TODO: is this even used anywhere?
//class ValueEvent(
//    key:String = autoKey(),
//    properties: Map<String, Any?> = mapOf(),
//    context: ContextInterface = LocalContext,
//): Event(key, properties, context) {
//
//    override val label = LocalContext.getLabel("ValueEvent", "Event", "Leaf", "EventPattern", "Pattern") { k, p, c -> ValueEvent(k, p, c) }
//
//    override val triggersLabel = context["Value"]
//
//    var value: Double? by this.properties
//
////    // TODO: worth keeping this?
//    override var machine: String? get() = "Event"
//        set(value) { this["machine"]="Event" }
//
//    var initValue: Double? by this.properties
//    var easing: String? by this.properties
//
//    var animateDur: Double? by this.properties
//
//    val isAnimation: Boolean get() = animateDur != null
//
//    val easingTyped get() = Easing.valueOf(easing ?: "None")
//
//    val durMs get() = dur?.let { (it * 1000.0).toLong() } ?: (0).toLong()
//
//    // TODO: maybe ... simplify?
//    val animateDurMs get() =  (animateDur?.let { (it * 1000.0).toLong() } ?: (0).toLong()).let {
//        if (it == (0).toLong() || it.absoluteValue > durMs) durMs else it
//    }
//
//    // val delay: Double? = null, // TODO maybe: (consider implementing)
//    // val initDur: Double? = null, // TODO maybe: (consider implementing)
//    //    val initEasing: Easing = Easing.None, // TODO maybe: (consider implementing)
//}
//
//
//
////    override val veins: Sequence<MutableMap<String, Any?>> get() = sequence {
////        var returning = true
//////        println(this@Cell.cuePath)
////        val namesIterators: List<Pair<String, Iterator<Any?>? >> = traverseNames.map {
////            val seq: Sequence<*>? = if (cuePath == null) {
//////                println("NO HERITAGE: " + this@Cell.toString())
////                this@Cell.getAs(it)
////            } else {
//////                println("YAY HERITAGE: " + this@Cell.toString())
////                this@Cell.propertiesWithHeritage[it] as Sequence<*>?
////            }
//////            if (cuePath == null) Pair(it, this@Cell.getAs<Sequence<*>>(it).iterator())
//////            else Pair(it, (this@Cell.propertiesWithHeritage[it] as Sequence<*>).iterator())
////            Pair(it, seq?.iterator())
////        }
////        while (returning) {
////            val returnMap = mutableMapOf<String, Any?>()
////            namesIterators.filter {it.second != null}.forEach {
////                if (it.second!!.hasNext()) returnMap[it.first] = it.second!!.next()
////                else returning = false
////            }
////            if (returning) yield(returnMap)
////        }







