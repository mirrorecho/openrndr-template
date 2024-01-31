package rain.graph

import rain.interfaces.*

class GraphRelationship(
    override val key:String,
    val relationshipType: String,
    override val source: GraphNode,
    override val target: GraphNode,
    properties: Map<String, Any?> = mapOf()
) : GraphableRelationship, GraphItem {

    override val properties: MutableMap<String, Any?> = properties.toMutableMap()

    // TODO: replace with label instance
    override val labels get() = listOf(this.relationshipType)

    override val primaryLabel = this.relationshipType

    override fun cleanup(graph: Graph) {
        graph.discardLabelIndex(this.relationshipType, this)
        this.source.sourcesFor.remove(this)
        this.target.targetsFor.remove(this)
    }
}