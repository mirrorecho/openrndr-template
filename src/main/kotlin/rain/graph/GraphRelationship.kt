package rain.graph

import rain.interfaces.*

class GraphRelationship(
    override val key:String,
    override val primaryLabel: String,
    override val source: GraphNode,
    override val target: GraphNode,
    properties: Map<String, Any?> = mapOf()
) : GraphableRelationship, GraphItem {

    override val properties: MutableMap<String, Any?> = properties.toMutableMap()

    // TODO: replace with label instance
    override val labels get() = listOf(primaryLabel)

    override fun cleanup(graph: Graph) {
        graph.discardLabelIndex(primaryLabel, this)
        source.sourcesFor.remove(this)
        target.targetsFor.remove(this)
    }
}