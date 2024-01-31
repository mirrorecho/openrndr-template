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
    val traverseNames: MutableSet<String> by this.properties.apply { putIfAbsent("traverseNames", mutableSetOf("dur", "machine")) }

    // TODO move the next two functions to be able to apply to any pattern?
    // .. and figure out how to automate?
    fun setVeins(key:String, vararg values: Any?) {
        this.properties[key] = sequenceOf(*values)
        traverseNames.add(key)
    }

    fun setVeinCycle(key:String, vararg values: Any?) {
        this.properties[key] = cycleOf(*values)
        traverseNames.add(key)
    }

    // the following probably not even needed since dur.sum() is so easy!
//    val sumDur: Double get() = dur.sum()

    override var dur: Sequence<Double> by this.properties
    override var machine: Sequence<String> by this.properties
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
                if (it.second!!.hasNext()) returnMap[it.first] = it.second!!.next() as Any
                else returning = false
            }
            if (returning) yield(returnMap)
        }
    }

}
