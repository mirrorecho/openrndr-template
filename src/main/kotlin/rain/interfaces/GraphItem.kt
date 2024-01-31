package rain.interfaces

import rain.graph.Graph

// TODO maybe: is this interface even worth it?
interface GraphItem: GraphableItem {

    fun cleanup(graph: Graph) {}

}