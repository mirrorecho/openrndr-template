package rain.interfaces

import rain.language.SelectNodes
import rain.language.SelectRelationships

interface SelectInterface {

    val labelName:String?

    val keys: List<String>?

    val properties: Map<String, Any?>?

    val selectFrom: SelectInterface?

    val direction: SelectDirection? // used only for relationships

    val rootSelect: SelectInterface get() = selectFrom?.rootSelect ?: this

}

interface NodeSelectable {
    val selectFrom: SelectInterface?
    val labelName:  String?

    fun select(vararg keys:String) =
        SelectNodes(keys.asList(), null, labelName, selectFrom)

    fun select(properties:Map<String,Any>?=null) =
        SelectNodes(null, properties, labelName, selectFrom)

    operator fun get(vararg selects: SelectNodes):SelectNodes {

    }

}

interface RelationshipSelectable {
    val selectFrom: SelectInterface?
    val labelName:  String

    fun select(vararg keys:String, direction:SelectDirection = SelectDirection.RIGHT) =
        SelectRelationships(keys.asList(), null, labelName, direction, selectFrom)

    fun select(properties:Map<String,Any>?=null, direction:SelectDirection = SelectDirection.RIGHT) =
        SelectRelationships(null, properties, labelName, direction, selectFrom)

    fun n(
        vararg keys: String,
        properties:Map<String,Any>?=null,
        labelName:String?=null
    ):SelectNodes {
        return SelectNodes(
            keys.asList(),
            properties,
            labelName,
            this,
        )
    }

}