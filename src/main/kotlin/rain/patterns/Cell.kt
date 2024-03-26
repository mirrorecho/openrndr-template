package rain.patterns

import org.openrndr.shape.sampleEquidistant
import rain.interfaces.*
import rain.language.*
import rain.utils.cycleOf

// a node that represents an iterable over a group nodes ... each of which is connected
// to this node, in a "pattern"
// TODO maybe: is Pattern really an interface
open class Cell(
    key:String = rain.utils.autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): CellPattern, Leaf(key, properties, context) {

    override val label = LocalContext.getLabel("Cell", "Leaf", "CellPattern", "Pattern") { k, p, c -> Cell(k, p, c) }

    override val isAlter = false

    // TODO: rename to veinNames
    //  AND IMPORTANT TODO ... how to automate (by method call that easily creates veins)
    val traverseNames: MutableSet<String> by this.properties.apply {
        putIfAbsent("traverseNames", mutableSetOf("dur", "machine"))
    }

    fun <BT:CellBuilder>build(callback: BT.()->Unit): Cell {
        val cb = CellBuilder(this)
        cb.apply(callback)
        return this
    }

    // TODO move the next two functions to be able to apply to any pattern?
    //  .. and figure out how to automate?
//      .. and naming?


    // CONSIDER THIS:
//    operator fun invoke(key:String):Vein {
//        return vein(key)
//    }

    fun ani( // just a shortcut (since this will be used so much!)
        value:Double,
        dur:Double? = 0.0, // 0.0 defaults to length of the event's dur (null is no animation)
        easing: String? = null,
        initValue:Double? = null, // TODO, consider implementing
    ): AnimateEvent {

        return AnimateEvent(value, dur, easing, initValue)
    }

    fun setVeinCycle(key:String, vararg values: Any?) {
        this.properties[key] = cycleOf(*values)
        traverseNames.add(key)
    }

    // the following probably not even needed since dur.sum() is so easy!
//    val sumDur: Double get() = dur.sum()

    override var dur: Sequence<Double> by this.properties

    override var machine: Sequence<String?> by this.properties

    val gate: Sequence<Boolean> by this.properties.apply { putIfAbsent("gate", cycleOf(false)) }

//    val dur: Sequence<Int> = sequenceOf(1).cycle()
//    val dur: Sequence<Int> = sequenceOf(1).cycle()

    override fun <T>propertyByVein(key: String): Sequence<T> = this.properties[key] as Sequence<T>

    // TODO: implement something like this for setting PARTIAL sequences of values
//    override fun setPropertyByVein(key: String, values:Sequence<Any>) {
//        veins.zip(values).forEach { it.first[key] = it.second }
//    }

    override var simultaneous: Boolean by this.properties.apply { putIfAbsent("simultaneous", false) }

//    // TODO: not elegant!
//    override fun setInitProperties(existingProperties: MutableMap<String, Any?>) {
//        super.setInitProperties(existingProperties)
//        existingProperties.putIfAbsent("simultaneous", false)
//    }


    // TODO: maybe this is a mutable map of triggers?
    override val veins: Sequence<MutableMap<String, Any?>> get() = sequence {
        var returning = true
//        println(this@Cell.cuePath)
        val namesIterators: List<Pair<String, Iterator<Any?>? >> = traverseNames.map {
            val seq: Sequence<*>? = if (cuePath == null) {
//                println("NO HERITAGE: " + this@Cell.toString())
                this@Cell.getAs(it)
            } else {
//                println("YAY HERITAGE: " + this@Cell.toString())
                this@Cell.propertiesWithHeritage[it] as Sequence<*>?
            }
//            if (cuePath == null) Pair(it, this@Cell.getAs<Sequence<*>>(it).iterator())
//            else Pair(it, (this@Cell.propertiesWithHeritage[it] as Sequence<*>).iterator())
            Pair(it, seq?.iterator())
        }
        while (returning) {
            val returnMap = mutableMapOf<String, Any?>()
            namesIterators.filter {it.second != null}.forEach {
                if (it.second!!.hasNext()) returnMap[it.first] = it.second!!.next()
                else returning = false
            }
            if (returning) yield(returnMap)
        }
    }

}

open class CellBuilder(
    val cell:Cell
) {
    fun vein(key:String): Vein {
        return Vein(cell, key)
    }

    fun value(vararg values:Double?): Vein {
        return vein("value")(*values)
    }
    fun dur(vararg values:Double?): Vein {
        return vein("dur")(*values)
    }
    fun animate(vararg values:Double?): Vein {
        return vein("animate")(*values)
    }
}

// TODO: play around with this... esp. with property heritage!
fun <BT:CellBuilder>cell(
    key:String = rain.utils.autoKey(),
    machine:String? = null,
    act:String? = machine,
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
    callback: BT.()->Unit
):Cell {
    Cell(key, properties, context).apply {
        machine?.let { setVeinCycle("machine", it) }
        act?.let { setVeinCycle("act", it) }
        build(callback)
        createMe()
        return this
    }
}





