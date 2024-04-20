package rain.interfaces

import rain.language.*

interface Selectable {
    val labelName:  String?
    val selectMe: SelectInterface
    val context: ContextInterface
    val graph: GraphInterface get() = context.graph
}

interface SelectInterface: Selectable {
    override val selectMe get() = this

    var selectFrom: SelectInterface?

    val keys: List<String>?

    val properties: Map<String, Any?>?

    val direction: SelectDirection? // used only for relationships

    val isRelationships: Boolean

    var rootSelect: SelectInterface get() = selectFrom?.rootSelect ?: this
        set(select) { rootSelect.selectFrom = select }

    fun selectKeys(): Sequence<String>

}

interface NodeSelectable: Selectable {
    override val selectMe: SelectNodes

    fun select(keys:List<String>?=null, properties:Map<String,Any>?=null) =
        SelectNodes(keys, null, labelName, selectMe)

    fun select(vararg keys:String) =
        select(keys.asList(), null)

    fun select(properties:Map<String,Any>?=null) =
        select(null, properties)

    fun relates(keys:List<String>?=null, properties: Map<String, Any>?= null, label:RelationshipLabel?=null, direction:SelectDirection = SelectDirection.RIGHT) =
        SelectRelationships(keys, properties, label?.labelName, direction, selectMe)

    fun relates(vararg keys:String, label:RelationshipLabel?=null, direction:SelectDirection = SelectDirection.RIGHT) =
        relates(keys.asList(), null, label, direction)

    fun relates(properties: Map<String, Any>, label:RelationshipLabel?=null, direction:SelectDirection = SelectDirection.RIGHT) =
        relates(null, properties, label, direction)

    fun relates(label:RelationshipLabel, direction:SelectDirection = SelectDirection.RIGHT) =
        relates(null, null, label, direction)

    // TODO: Test!!!
    operator fun get(vararg selects: SelectNodes):SelectNodes {
        var returnSelect: SelectNodes = selectMe
        selects.forEach { it.rootSelect = returnSelect; returnSelect = it}
//        fun printSelects(s:SelectInterface) {
//            println(s)
//            println("RESULT KEYS: ${s.selectKeys().toList()}")
//            println("------------")
//            s.selectFrom?.let { printSelects(it) }
//        }
//        printSelects(returnSelect)
        return returnSelect
    }

}

interface RelationshipSelectable: Selectable {
    override val selectMe: SelectRelationships

    fun select(keys:List<String>?=null, properties: Map<String, Any>?= null, direction:SelectDirection = SelectDirection.RIGHT) =
        SelectRelationships(keys, properties, labelName, direction, selectMe)

    fun select(vararg keys:String, direction:SelectDirection = SelectDirection.RIGHT) =
        select(keys.asList(), null, direction)

    fun select(properties:Map<String,Any>?=null, direction:SelectDirection = SelectDirection.RIGHT) =
        select(null, properties, direction)

    fun nodes(keys:List<String>?=null, properties: Map<String, Any>?= null, labelName:String?=null) =
        SelectNodes(keys, properties, labelName, selectMe)

    fun nodes(vararg keys:String, labelName:String?=null) =
        nodes(keys.asList(), null, labelName)

    fun nodes(properties: Map<String, Any>?= null, labelName:String?=null) =
        nodes(null, properties, labelName)

}