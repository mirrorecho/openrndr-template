package rain.language

import rain.graph.GraphNode
import rain.interfaces.*
import rain.utils.autoKey
import kotlin.reflect.KClass

open class NodeLabel<T:Node>(
    myClass: KClass<T>,
    parentLabel:NodeLabel<*>? = null,
    override val factory: (String)->T,
    ): NodeSelectable, NodeLabelInterface<T> {

    private fun getName(cl:KClass<T>) = cl.simpleName ?: "Node"

    override val isRoot: Boolean = parentLabel==null

    override val labelName:String = getName(myClass)

    override val allNames: List<String> = listOf(getName(myClass)) + parentLabel?.allNames.orEmpty()
    override val selectMe:SelectNodes = SelectNodes(labelName=getName(myClass))

    override var context: ContextInterface = LocalContext // TODO: ok to just default to LocalContext here?

    override val registry: MutableMap<String, T> = mutableMapOf()

    override fun toString() = labelName

}
