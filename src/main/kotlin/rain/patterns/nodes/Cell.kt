package rain.patterns.nodes

import rain.language.*
import rain.utils.autoKey

// ===========================================================================================================

open class Cell(
    key:String = autoKey(),
): EventTree(key) {
    companion object : NodeCompanion<Cell>(EventTree.childLabel { k -> Cell(k) })
    override val label: NodeLabel<out Cell> = Cell.label

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

    override var simultaneous: Boolean by this.properties.apply { putIfAbsent("simultaneous", false) }

    operator fun invoke(block: Cell.()->Unit): Cell {
        apply(block)
        saveDown()
        return this
    }

    // TODO: confirm this works!
    fun stream(name:String, vararg values: Any?) {
        val leavesIterator = this.leaves.asSequence().iterator()
        val valuesIterator = values.iterator()
        while (valuesIterator.hasNext()) {
            if (leavesIterator.hasNext()) {
                leavesIterator.next()[name] = valuesIterator.next()
            } else {
                extend(
                    Event.create { this[name] = valuesIterator.next() }
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
