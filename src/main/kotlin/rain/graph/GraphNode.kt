package rain.graph

import rain.interfaces.*

class GraphNode(
    override val key:String,
    override val labels: List<String>,
    properties: Map<String, Any?> = mapOf()
) : GraphableNode, GraphItem {

    override fun toString():String = "GRAPH NODE: $labelName($key) $properties"

    override val properties: MutableMap<String, Any?> = properties.toMutableMap()

    //maps for faster indexing ... keys are relationships, values are the target nodes
    internal val sourcesFor = mutableMapOf<GraphRelationship, GraphNode>()
    internal val targetsFor = mutableMapOf<GraphRelationship, GraphNode>()

    override val labelName = labels[0]

    override fun cleanup(graph: Graph) {
        labels.forEach { graph.discardLabelIndex(it, this) }

        // TODO better to use asSequence or asIterable?
        sourcesFor.asIterable().forEach { graph.deleteRelationship(it.key.key) }
        targetsFor.asIterable().forEach { graph.deleteRelationship(it.key.key) }
    }
}