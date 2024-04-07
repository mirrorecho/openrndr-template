package rain.interfaces

import rain.language.SelectRelationships

interface GraphInterface {

    // NOTE: this was a dunder method in python implementation
    fun contains(key: String): Boolean

    // NOTE: this was named exists in python implementation
    fun contains(node:GraphableNode): Boolean

    fun contains(relationship:GraphableRelationship): Boolean

    fun create(node:GraphableNode)

    fun create(relationship:GraphableRelationship)

    fun merge(node:GraphableNode)

    fun merge(relationship:GraphableRelationship)

    fun read(node:GraphableNode)

    fun read(relationship:GraphableRelationship)

    // TODO: assume not needed
//    fun readRelationship(item:GraphableItem)

    fun save(node:GraphableNode)

    fun save(relationship:GraphableRelationship)

    fun deleteNode(key: String)

    fun deleteRelationship(key: String)

    fun <T: LanguageNode>selectNodes(select: SelectNodeInterface<T>):Sequence<T>

    fun <T: LanguageRelationship>selectRelationships(select: SelectRelationshipInterface<T>):Sequence<T>

//    fun <T: LanguageItem>selectItems(select: SelectInterface, factory:):Sequence<T>

}

