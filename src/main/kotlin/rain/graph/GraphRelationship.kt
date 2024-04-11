package rain.graph

import rain.interfaces.*

class GraphRelationship(
    override val key:String,
    override val labelName: String,
    override val source: GraphNode,
    override val target: GraphNode,
    properties: Map<String, Any?> = mapOf()
) : GraphableRelationship, GraphItem {

    override fun toString():String = "GRAPH RELATIONSHIP: (${source.key} $labelName ${target.key} | $key) $properties"

    override val properties: MutableMap<String, Any?> = properties.toMutableMap()

    // TODO: replace with label instance
    override val labels get() = listOf(labelName)

    override fun cleanup(graph: Graph) {
        graph.discardLabelIndex(labelName, this)
        source.sourcesFor.remove(this)
        target.targetsFor.remove(this)
    }
}