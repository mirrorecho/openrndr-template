package rain.interfaces

interface GraphInterface {

    // NOTE: this was a dunder method in python implementation
    fun contains(key: String): Boolean

    // NOTE: this was named exists in python implementation
    fun contains(item:GraphableItem): Boolean

    fun create(item:GraphableItem)

    fun merge(item:GraphableItem)

    fun read(item:GraphableItem)

    // TODO: assume not needed
//    fun readRelationship(item:GraphableItem)

    fun save(item:GraphableItem)

    fun delete(key: String)

    fun <T: LanguageNode>selectNodes(select: SelectInterface<T>):Sequence<T>

    fun <T: LanguageRelationship>selectRelationships(select: SelectInterface<T>):Sequence<T>

//    fun <T: LanguageItem>selectItems(select: SelectInterface, factory:):Sequence<T>

}

