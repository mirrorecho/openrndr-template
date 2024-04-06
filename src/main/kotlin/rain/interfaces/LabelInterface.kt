package rain.interfaces

import rain.utils.autoKey

interface LabelInterface<T:LanguageItem> {
    val name:String
    val allNames: List<String>
    val isRoot: Boolean
    val isRelationship:Boolean
    var context: ContextInterface
}

interface NodeLabelInterface<T:LanguageNode>: LabelInterface<T> {
    val factory: (String)->T
    override val isRelationship: Boolean get() = false

    fun from(gNode:GraphableNode):T =
        factory(gNode.key).apply { updatePropertiesFrom(gNode) }

    fun merge(key:String = autoKey(), block:( T.()->Unit )?=null ):T =
        factory(key).apply { block?.invoke(this); mergeMe() }

    fun create(key:String = autoKey(), block:( T.()->Unit )?=null ):T =
        factory(key).apply { block?.invoke(this); createMe() }

}

interface RelationshipLabelInterface<T:LanguageRelationship>: LabelInterface<T> {
    override val isRelationship: Boolean get() = true
    override val isRoot: Boolean get() = true

    fun from(gRelationship:GraphableRelationship):T = gRelationship.run {
        factory(key, source.key, target.key).also { it.updatePropertiesFrom(this) }
    }

    fun merge(sourceKey:String, targetKey:String, key:String = autoKey(), block:( T.()->Unit )?=null ):T =
        factory(sourceKey, targetKey, key).apply { block?.invoke(this); mergeMe() }

    fun create(sourceKey:String, targetKey:String, key:String = autoKey(),block:( T.()->Unit )?=null ):T =
        factory(sourceKey, targetKey, key).apply { block?.invoke(this); createMe() }

    fun factory(sourceKey:String, targetKey:String, key:String = autoKey()): T

}