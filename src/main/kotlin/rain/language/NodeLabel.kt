package rain.language

import rain.interfaces.*

class NodeLabel<T:Node>(
    override val labelName:String,
    override val factory: (String)->T,
    override val isRoot: Boolean = false,
    parentNames:List<String>,
): NodeSelectable, NodeLabelInterface<T> {
    override val allNames: List<String> = listOf(labelName) + parentNames
    override val selectMe = SelectNodes(labelName=this.labelName)
    override var context: ContextInterface = LocalContext // TODO: ok to just default to LocalContext here?
    private val registry = NodeRegistry(label=this)

    override fun paletteGetOrPut(key:String, defaultNode:()->T):T = registry.getOrPut(key, defaultNode)

}

inline fun <reified T:Node>rootLabel(noinline factory:(String)->T): NodeLabel<T> {
    return NodeLabel(
        T::class.simpleName ?: "Item",
        factory,
        true,
        listOf(),
    )
}