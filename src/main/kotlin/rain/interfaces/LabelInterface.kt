package rain.interfaces

import rain.utils.autoKey

interface LabelInterface<T:LanguageItem> {
    val labelName:String
    val allNames: List<String>
    val isRoot: Boolean
    val isRelationship:Boolean
    var context: ContextInterface
}

interface NodeLabelInterface<T:LanguageNode>: LabelInterface<T> {
    override val isRelationship: Boolean get() = false

    val factory: (String)->T

    fun from(gNode:GraphableNode):T =
        factory(gNode.key).apply { updatePropertiesFrom(gNode) }

    fun merge(key:String = autoKey(), properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T =
        factory(key).apply {
            properties?.let{ this.properties.putAll(it) };
            mergeMe();
            block?.invoke(this);
        }

    fun create(key:String = autoKey(), properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T =
        factory(key).apply {
            properties?.let{ this.properties.putAll(it) };
            createMe();
            block?.invoke(this);
        }

}

interface RelationshipLabelInterface<T:LanguageRelationship>: LabelInterface<T> {
    override val isRelationship: Boolean get() = true
    override val isRoot: Boolean get() = false

    fun factory(sourceKey:String, targetKey:String, key:String = autoKey() ): T

    fun from(gRelationship:GraphableRelationship):T = gRelationship.run {
        factory(source.key, target.key, key).also { it.updatePropertiesFrom(this) }
    }

    fun merge(sourceKey:String, targetKey:String, key:String, properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T =
        factory(sourceKey, targetKey, key).apply {
            properties?.let{ this.properties.putAll(it) };
            mergeMe();
            block?.invoke(this);
        }

    fun create(sourceKey:String, targetKey:String, key:String = autoKey(), properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T =
        factory(sourceKey, targetKey, key).apply {
            properties?.let{ this.properties.putAll(it) };
            createMe();
            block?.invoke(this);
        }

}