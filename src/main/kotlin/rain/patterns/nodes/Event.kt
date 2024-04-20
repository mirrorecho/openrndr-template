package rain.patterns.nodes

import rain.language.*
import rain.rndr.nodes.RndrMachine
import rain.utils.autoKey

class Event(
    key:String = autoKey(),
): Tree<Event>(key) {
    companion object : NodeCompanion<Event>(Node.childLabel { k -> Event(k) })
    override val label: NodeLabel<out Event> = Event.label

    override val thisTyped = this

    // TODO... no, maybe this should just point to a node on the graph right away!
//    var machine:NodeLabel<out RndrMachine>? get() = getUp("machine")
//        set(value) { this["machine"]=value }

//    var machinePath: List<RelationshipLabel>? get() = getUp("machinePath")
//        set(value) { this["machinePath"]=value }

//    var machineKey: String? get() = getUp("machineKey")
//        set(value) { this["machineKey"]=value }

    var gate: Boolean? get() = getUp("gate")
        set(value) { this["gate"]=value }

    var simultaneous: Boolean by this.properties.apply { putIfAbsent("simultaneous", false) }

    val sumDur: Double get() =
        if (isLeaf) getUp("dur")
        else {
            children.map { sumDur }.run {
                if (simultaneous) { max() } else { sum() }
            }
        }



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
