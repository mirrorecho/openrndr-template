package rain.graph

import rain.interfaces.*
import kotlin.Exception

class Graph: GraphInterface {
    // TODO: make private after debugging:
    val graphNodes: MutableMap<String, GraphNode> = mutableMapOf()
    val graphRelationships: MutableMap<String, GraphRelationship> = mutableMapOf()

    val nodeLabelIndex: MutableMap<String, MutableMap<String, GraphNode>> = mutableMapOf()
    val relationshipLabelIndex: MutableMap<String, MutableMap<String, GraphRelationship>> = mutableMapOf()

    override fun contains(key:String) = key in graphNodes || key in graphRelationships

    override fun contains(node: GraphableNode) = node.key in graphNodes
    override fun contains(relationship: GraphableRelationship) = relationship.key in graphRelationships

    // =================================================================================

    private fun addLabelIndex(label: String, graphNode: GraphNode) {
        nodeLabelIndex.getOrPut(label) { mutableMapOf() }[graphNode.key] = graphNode
    }

    private fun addLabelIndex(label: String, graphRelationship: GraphRelationship) {
        relationshipLabelIndex.getOrPut(label) { mutableMapOf() }[graphRelationship.key] = graphRelationship
    }

    // =================================================================================

    internal fun discardLabelIndex(label: String, graphNode: GraphNode) {
        nodeLabelIndex[label]?.remove(graphNode.key)
    }

    internal fun discardLabelIndex(label: String, graphRelationship: GraphRelationship) {
        relationshipLabelIndex[label]?.remove(graphRelationship.key)
    }

    // =================================================================================

    override fun create(node: GraphableNode) {
        graphNodes.putIfAbsent(node.key,
            GraphNode(node.key, node.labels, node.properties).also {
                node.labels.forEach { label -> addLabelIndex(label, it) }
            }
        )?.let {
            throw Exception("Node ${node.key} already exists in graph. So it can not be created.")
        }
    }

    override fun create(relationship: GraphableRelationship) {
        graphNodes[relationship.source.key]?.let { source ->
            graphNodes[relationship.target.key]?.let { target ->

                graphRelationships.putIfAbsent(relationship.key,
                    GraphRelationship(
                        relationship.key,
                        relationship.labelName,
                        source,
                        target,
                        relationship.properties
                    ).also {
                        source.sourcesFor[it] = target
                        target.targetsFor[it] = source
                        addLabelIndex(it.labelName, it)
                    }
                )?.let {
                    throw Exception("Relationship ${relationship.key} already exists in graph. So it can not be created.")
                }
                return // OK!
            }
            throw Exception("Target ${relationship.target.key} not found in graph. Relationship not created.")
        }
        throw Exception("Source ${relationship.source.key} not found in graph. Relationship not created.")
    }

    // =================================================================================

    override fun merge(node: GraphableNode) {
        graphNodes[node.key]?.let { it.updatePropertiesFrom(node); return }
        create(node)
    }

    override fun merge(relationship: GraphableRelationship) {
        graphRelationships[relationship.key]?.let { it.updatePropertiesFrom(relationship); return }
        create(relationship)
    }

    // =================================================================================

    override fun read(node: GraphableNode) {
        graphNodes[node.key]?.let { node.updatePropertiesFrom(it); return }
        throw Exception("Node ${node.key} not found in graph; could not be read.")
    }

    override fun read(relationship: GraphableRelationship) {
        graphRelationships[relationship.key]?.let { relationship.updatePropertiesFrom(it); return }
        throw Exception("Relationship ${relationship.key} not found in graph; could not be read.")
    }

    // =================================================================================

    override fun save(node: GraphableNode) {
        graphNodes[node.key]?.let { it.updatePropertiesFrom(node); return }
        throw Exception("Node ${node.key} not found in graph; could not save.")
    }

    override fun save(relationship: GraphableRelationship) {
        graphRelationships[relationship.key]?.let { it.updatePropertiesFrom(relationship); return }
        throw Exception("Relationship ${relationship.key} not found in graph; could not save.")
    }

    // =================================================================================

    override fun deleteNode(key: String) {
        this.graphNodes[key]?.cleanup(this)
        this.graphNodes.remove(key)
    }

    override fun deleteRelationship(key: String) {
        this.graphRelationships[key]?.cleanup(this)
        this.graphRelationships.remove(key)
    }

    // =================================================================================

    fun clear() {
        graphNodes.clear()
        graphRelationships.clear()
        nodeLabelIndex.clear()
        relationshipLabelIndex.clear()
    }

    val nodeSize get() = graphNodes.size
    val relationshipSize get() = graphRelationships.size

    val size get() = nodeSize + relationshipSize

    // =================================================================================
    // =================================================================================


    // =================================================================================


    override fun selectGraphNodes(select: SelectInterface): Sequence<GraphNode> {
        fun getNewSequence() = (
                select.labelName?.let { nodeLabelIndex[select.labelName].orEmpty() } ?: graphNodes
                ).sequenceKeys(select.keys).filterProperties(select.properties)

        select.selectFrom?.let { sf ->
            val fromSequence = (when (sf.direction) {
                SelectDirection.RIGHT -> selectGraphRelationships(sf).map { r -> r.target }
                SelectDirection.LEFT -> selectGraphRelationships(sf).map { r -> r.source }
                else -> selectGraphNodes(sf) // TODO: test this
            })
            if (sf.direction == SelectDirection.CONCAT) return sequence {
                yieldAll(fromSequence); yieldAll(getNewSequence());
            }
            else return fromSequence.filterKeys(select.keys).filterLabel(select.labelName)
                .filterProperties(select.properties)

        }
        return getNewSequence()
    }


    override fun selectGraphRelationships(select:SelectInterface):Sequence<GraphRelationship> =
        (select.selectFrom?.let {sf ->
            (if (sf.isRelationships) selectGraphRelationships(sf)
            else when (select.direction) {
                SelectDirection.RIGHT -> sequence {
                    selectGraphNodes(sf).forEach { n -> yieldAll(n.sourcesFor.map{ it.key }) }
                }
                SelectDirection.LEFT ->  sequence {
                    selectGraphNodes(sf).forEach { n -> yieldAll(n.targetsFor.map{ it.key }) }
                }
                else -> selectGraphRelationships(sf) // TODO: test this (should simply further filter the select
            }).filterLabel(select.labelName).filterKeys(select.keys)
        } ?: run {
            (select.labelName?.let { relationshipLabelIndex[select.labelName].orEmpty() } ?: graphRelationships
                    ).sequenceKeys(select.keys)
        }).filterProperties( select.properties )


//    private fun selectLocalItems(select:SelectInterface<*>):Sequence<GraphItem> {
//        var mySequence: Sequence<GraphItem>
//
//        if (select.selectFrom != null) {
//            val fromSequence = selectLocalItems(select.selectFrom!!)
//            mySequence = when (select.direction) {
//
//                // TODO: refactor to deal with nods or relationships without having to cast
//
//                SelectDirection.RIGHT -> sequence {
//                    fromSequence.forEach { n ->
//                        (n as GraphNode).sourcesFor.forEach { yield(it.key) }
//                    }
//                }
//                SelectDirection.LEFT ->  sequence {
//                    fromSequence.forEach { n ->
//                        (n as GraphNode).targetsFor.forEach { yield(it.key) }
//                    }
//                }
//                SelectDirection.RIGHT_NODE -> fromSequence.map { r -> (r as GraphRelationship).target }
//                SelectDirection.LEFT_NODE -> fromSequence.map { r -> (r as GraphRelationship).source }
//                else -> sequenceOf()
//            }
//            // WARNING: this implementation differs from the key select
//            // below for the original select ... it's purely a filter ... won't re-order or duplicate items
//            mySequence = mySequence.filterKeys(select.keys)
//
//            if (!select.keys.isNullOrEmpty()) mySequence = mySequence.filter { select.keys!!.contains(it.key) }
//
//            if (!select.label.isRoot) mySequence = mySequence.filter { it.labels.contains(select.label.name) }
//
//        } else {
//            val myMap = if (select.label.isRoot) data else typeIndex[select.label.name].orEmpty()
//
//            mySequence = if (!select.keys.isNullOrEmpty() )
//                select.keys?.asSequence().orEmpty().mapNotNull { k-> myMap[k] }
//            else
//                myMap.asSequence().map {it. value }
//        }
//        if (!select.properties.isNullOrEmpty()) mySequence = mySequence.filter { it.anyPropertyMatches(select.properties!!) }
//    return mySequence
//    }

}

fun <T:GraphItem>Map<String, T>.sequenceKeys(keys:List<String>?=null):Sequence<T> {
    keys?.let { ks-> return ks.asSequence().mapNotNull { k-> this[k] } }
    return this.asSequence().map { it.value }
}

fun <T:GraphItem>Sequence<T>.filterLabel(labelName:String?):Sequence<T> {
    labelName?.let { return this.filter { it.labels.contains(labelName) } }
    return this
}

fun <T:GraphItem>Sequence<T>.filterKeys(keys:List<String>?=null):Sequence<T> {
    keys?.let { ks-> return this.filter { ks.contains(it.key) } }
    return this
}

fun <T:GraphItem>Sequence<T>.filterProperties(properties:Map<String, Any?>?=null):Sequence<T> {
    properties?.let { ps-> return this.filter { it.anyPropertyMatches(ps) } }
    return this
}

//fun Sequence<GraphRelationship>.filterProperties(properties:Map<String, Any?>?=null):Sequence<GraphRelationship> {
//    properties?.let { ps-> return this.filter { it.anyPropertyMatches(ps) } }
//    return this
//}