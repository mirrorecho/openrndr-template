package rain.patterns

import rain.interfaces.*
import rain.language.*
import rain.utils.autoKey

// ===========================================================================================================

open class Cell(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): EventTree(key, properties, context) {

//    class StreamHelper(
//        val name:String,
//        val cell:Cell,
//    ) {
//        operator fun invoke(vararg stream: Any?) {
//            cell.apply {
//                // TODO: logic here for adding/updating events based on stream values
//            }
//        }
//    }

    override val label = LocalContext.getLabel<Node>("Cell", "EventTree", "Tree", "EventPattern", "Pattern") { k, p, c -> Cell(k, p, c) }

    override var simultaneous: Boolean by this.properties.apply { putIfAbsent("simultaneous", false) }

    operator fun invoke(block:Cell.()->Unit): Cell {
        apply(block)
        saveDown()
        return this
    }

    fun stream(name:String, vararg values: Any?) {
        val leavesIterator = this.leaves.asSequence().iterator()
        val valuesIterator = values.iterator()
        while (valuesIterator.hasNext()) {
            if (leavesIterator.hasNext()) {
                leavesIterator.next()[name] = valuesIterator.next()
            } else {
                extend(
                    Event(properties = mapOf(name to valuesIterator.next())).apply { createMe() }
                )
            }

        }
    }

//    fun stream(name:String) = StreamHelper(name, this)
//    override operator fun get(name: String): StreamHelper {
//        return StreamHelper(name, this)
//    }


//    override operator fun set(name: String, value: Any?) {
//        this[name] = value
//    }

    // TODO: use something like this?
//    operator fun invoke(vararg patterns: Pattern): CellTree {
//        this.extend(*patterns)
//        return this
//    }

        // TODO: implement cell building
//    fun <BT: CellBuilder> build(callback: BT.() -> Unit): Cell {
//        val cb = CellBuilder(this)
//        cb.apply(callback)
//        return this
//    }

}

// ===========================================================================================================

// TODO maybe: type param here?
fun cell(
    key:String= autoKey(),
    machine:String?=null,
    machineKey:String?=null,
    simultaneous:Boolean=false,
    context: ContextInterface = LocalContext,
    block:Cell.()->Unit): Cell {

    return Cell(key, context=context).apply {
        machine?.let { this["machine"]=it }
        machineKey?.let { this["machineKey"]=it }
        this["simultaneous"] = simultaneous
        createMe()
        this.invoke(block)
    }
}