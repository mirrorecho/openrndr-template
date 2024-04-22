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

    val registry: MutableMap<String, T>

    fun get(key:String):T =
        registry.getOrPut(key) {
            factory(key).apply {
                context.graph.read(this)
            }
        }

    fun from(gNode:GraphableNode):T =
        registry.getOrPut(gNode.key) {
            factory(gNode.key).apply {
                updatePropertiesFrom(gNode)
            }
        }


    fun merge(key:String = autoKey(), properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T =
        registry.getOrPut(key) { factory(key) }.also { node->
            properties?.let{ node.updatePropertiesFrom(it) };
            context.graph.merge(node)
            block?.invoke(node)
        }

    fun create(key:String = autoKey(), properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T =
        factory(key).apply {
            properties?.let{ this.updatePropertiesFrom(it) }
            context.graph.create(this)
            registry[key] = this
            block?.invoke(this)
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
            context.graph.merge(this);
            block?.invoke(this);
        }

    fun create(sourceKey:String, targetKey:String, key:String = autoKey(), properties:Map<String,Any?>?=null, block:( T.()->Unit )?=null ):T =
        factory(sourceKey, targetKey, key).apply {
            properties?.let{ this.properties.putAll(it) };
            context.graph.create(this);
            block?.invoke(this);
        }

}