package rain.patterns

import rain.interfaces.*
import rain.language.*

// ===========================================================================================================

open class Cell(
    key:String = rain.utils.autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): EventTree(key, properties, context) {

    override val label = LocalContext.getLabel("Cell", "EventTree", "Tree", "EventPattern", "Pattern") { k, p, c -> Cell(k, p, c) }

    override var simultaneous: Boolean by this.properties.apply { putIfAbsent("simultaneous", false) }


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