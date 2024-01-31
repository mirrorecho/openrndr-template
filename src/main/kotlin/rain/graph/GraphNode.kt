package rain.graph

import rain.interfaces.*

class GraphNode(
    override val key:String,
    override val labels: List<String>,
    properties: Map<String, Any?> = mapOf()
) : GraphableNode, GraphItem {

    override val properties: MutableMap<String, Any?> = properties.toMutableMap()

    //maps for faster indexing ... keys are relationships, values are the target nodes
    internal val sourcesFor = mutableMapOf<GraphRelationship, GraphNode>()
    internal val targetsFor = mutableMapOf<GraphRelationship, GraphNode>()

    override val primaryLabel = labels[0]

    override fun cleanup(graph: Graph) {
        this.labels.forEach { graph.discardLabelIndex(it, this) }

        // TODO better to use asSequence or asIterable?
        this.sourcesFor.asIterable().forEach { graph.delete(it.key.key) }
        this.targetsFor.asIterable().forEach { graph.delete(it.key.key) }
    }
}