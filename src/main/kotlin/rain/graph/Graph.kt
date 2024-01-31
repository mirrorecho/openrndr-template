package rain.graph

import rain.interfaces.*

class Graph: GraphInterface {
    // TODO: rename to keyIndex
    private val data: MutableMap<String, GraphItem> = mutableMapOf()
    // TODO maybe ... implement nodes and relationships as separate maps?
    // that way could handle getting either by key without downcasting

    // TODO: labelIndex would be a better name
    private val typeIndex: MutableMap<String, MutableMap<String, GraphItem>> = mutableMapOf()


    override fun contains(key: String): Boolean = this.data.contains(key)

    override fun contains(item:GraphableItem): Boolean = this.data.contains(item.key)

    // NOTE: dunder method in python
//    private fun getItem(key: String): GraphableItem? = this.data[key]

    private fun getNode(key: String): GraphNode {
        return this.data[key] as GraphNode // downcast and null safe bypass reasonable here?
    }

    private fun getRelationship(key: String): GraphRelationship {
        return this.data[key] as GraphRelationship // ditto, downcast and null safe bypass reasonable here?
    }

    // TODO: combine with something that returns the items to avoid redundant lookups, such as checkItem below
    // TODO: also, make private?
    internal fun checkKey(key:String, exists:Boolean = true) {
        if (this.data.contains(key) != exists)
            throw Exception(key + if (exists) "does not exist!" else " already exists!")
    }

//    internal fun checkItem(key:String, exists:Boolean = true): GraphableItem {
//        if (this.data.contains(key) != exists)
//            throw Exception(key + if (exists) "does not exist!" else " already exists!")
//    }

    private fun addLabelIndex(label:String, graphItem: GraphItem) {
        this.typeIndex.getOrPut(label) { mutableMapOf() } [graphItem.key] = graphItem
    }

    internal fun discardLabelIndex(label:String, graphItem: GraphItem) {
        this.typeIndex[label]?.remove(graphItem.key)
    }

    // =================================================================================

    private fun createGraphNode(key:String, labels:List<String> = listOf(), properties: Map<String, Any?> = mapOf()) {
        var node = GraphNode(key, labels, properties)
        this.data[key] = node
        labels.forEach { this.addLabelIndex(it, node) }
    }

    private fun createGraphRelationship(key:String, relationshipType:String,
                                        sourceKey:String, targetKey:String, properties: Map<String, Any?> = mapOf()) {
        this.checkKey(sourceKey)
        this.checkKey(targetKey)

        var source = this.getNode(sourceKey)
        var target = this.getNode(targetKey)

        var relationship = GraphRelationship(key, relationshipType,
            source, target, properties)

        this.data[key] = relationship
        source.sourcesFor[relationship] = target
        target.targetsFor[relationship] = source
        this.addLabelIndex(relationshipType, relationship)
    }

// seems unnecessary
//    fun getProperties(key:String): MutableMap<String, Any>? = this.data[key]?.properties

    override fun create(item:GraphableItem) {
        this.checkKey(item.key, false)
        when (item) {
            is GraphableNode -> this.createGraphNode(item.key, item.labels, item.properties)
            is GraphableRelationship -> this.createGraphRelationship(item.key, item.primaryLabel, item.source.key, item.target.key, item.properties)
        }
    }

    override fun merge(item:GraphableItem) {
//        this.data[item.key]?.let {} else

        // TODO: refactor to avoid multiple lookups


        if (this.contains(item.key)) {
            // note, not calling save here to avoid checking for key twice
            this.data[item.key]?.properties?.putAll(item.properties)
        } else {
            // ditto
            // but TODO, could by DRYer here, rather than replicating create logic...
            // ... think about "safe" methods that check for keys, vs unsafe that don't
            when (item) {
                is GraphableNode -> this.createGraphNode(item.key, item.labels, item.properties)
                is GraphableRelationship -> this.createGraphRelationship(item.key, item.primaryLabel, item.source.key, item.target.key, item.properties)
            }
        }
    }

    override fun read(item: GraphableItem) {
        this.checkKey(item.key) // TODO note this duplicates key checking
        this.data[item.key]?.let { item.updatePropertiesFrom(it.properties) }
    }

// TODO: needed????
    // NOTE: original python implementation named this get_relationship
//    fun readRelationship(item: GraphableRelationship) {
//        // TODO this takes multiple lookups... not cool
//        this.read(item)
//        var relationship = this.getRelationship(item.key)
//        var source = relationship.source
//        var target = relationship.target
//        item.setSource(source.primaryLabel, source.key)
//        item.setTarget(target.primaryLabel, target.key)
//    }

    override fun save(item: GraphableItem) {
        this.checkKey(item.key)
        this.data[item.key]?.let { it.updatePropertiesFrom(item.properties) }
    }

    override fun delete(key:String) {
        this.data[key]?.cleanup(this)
        this.data.remove(key)
    }

    fun clear() {
        this.data.clear()
        // TODO maybe - also clear typeIndex?
    }

    val size: Int get() = this.data.size

    override fun <T: LanguageItem>selectItems(select: SelectInterface):Sequence<T> = sequence {
        selectLocalItems(select).forEach {
            yield(
                if (it is GraphRelationship) select.context.makeRelationship(it)
                else select.context.make(it)
            )
        }
    }

    private fun selectLocalItems(select:SelectInterface):Sequence<GraphItem> {
        var mySequence: Sequence<GraphItem>

        if (select.selectFrom != null) {
            val fromSequence = selectLocalItems(select.selectFrom!!)
            mySequence = when (select.direction) {
                SelectDirection.RIGHT -> sequence {
                    fromSequence.forEach { n ->
                        (n as GraphNode).sourcesFor.forEach { yield(it.key) }
                    }
                }
                SelectDirection.LEFT ->  sequence {
                    fromSequence.forEach { n ->
                        (n as GraphNode).targetsFor.forEach { yield(it.key) }
                    }
                }
                SelectDirection.RIGHT_NODE -> fromSequence.map { r -> (r as GraphRelationship).target }
                SelectDirection.LEFT_NODE -> fromSequence.map { r -> (r as GraphRelationship).source }
                else -> sequenceOf()
            }
            // WARNING: this implementation differs from the key select
            // below for the original select ... it's purely a filter ... won't re-order or duplicate items
            if (!select.keys.isNullOrEmpty()) mySequence = mySequence.filter { select.keys!!.contains(it.key) }

            if (!select.label.isNullOrBlank()) mySequence = mySequence.filter { it.labels.contains(select.label!!) }

        } else {
            val myMap = if (select.label.isNullOrBlank()) data else typeIndex[select.label].orEmpty()

            mySequence = if (!select.keys.isNullOrEmpty() )
                select.keys?.asSequence().orEmpty().mapNotNull { k-> myMap[k] }
            else
                myMap.asSequence().map {it. value }
        }
        if (!select.properties.isNullOrEmpty()) mySequence = mySequence.filter { it.anyPropertyMatches(select.properties!!) }
    return mySequence
    }

    fun testMe(){
//        this.selectItems()
    }

}