package rain.language

import rain.interfaces.*
import rain.utils.autoKey


// ===========================================================================================================

abstract class Item(
    override val key:String,
    properties: Map<String, Any?>,
    override val context: ContextInterface
): LanguageItem {

    // TODO: not so elegant way to initialize properties... rethink?
    open fun setInitProperties(existingProperties: MutableMap<String, Any?>) {
    }

    final override val properties: MutableMap<String, Any?> = properties.toMutableMap().apply { setInitProperties(this) }

    override val selectSelf: Select get() = SelfSelect(this.context, this)

    fun <T>getFancyProperty(fancyName: String): FancyProperty<T> {
        return context.getFancyProperty(fancyName)
    }

    fun setFancyProperty(fancyProperty: FancyProperty<*>) {
        properties[fancyProperty.name] = fancyProperty.graphValue
        context.setFancyProperty(fancyProperty)
    }

    // TODO: consider if a setProperty is warranted as well
    fun <T>getProperty(name: String): T? {
        properties[name]?.let {
            with(it.toString()) {
                if (this.startsWith(":FANCY:")) {
                    return this@Item.getFancyProperty<T>(this.substringAfter(":FANCY:")).value
                } else {
                    return it as T
                }
            }
        }
        return null
    }

    // TODO: will I end up using this?
    fun setProperty(name: String, value: Any, isFancy:Boolean=false) {
        if (isFancy) {
            setFancyProperty(FancyProperty(name, value, context)) // TODO: ?? hmm why is this OK as opposed to needing setFancyProperty<T>
        } else {
            this.properties[name] = value
        }
    }

}

// ===========================================================================================================

open class Node(
    key:String = autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context:ContextInterface = LocalContext,
): LanguageNode, Item(key, properties, context) {

    // TODO: move into class args? or just list of labels in class args?
    override val label = LocalContext.getLabel("NODE") { k, p, c -> Node(k, p, c) }

    override fun r(direction: SelectDirection, label:String?, keys:List<String>?, properties:Map<String,Any>?): TargetedRelationshipSelect =
        selectSelf.r(direction=direction, label=label, keys=keys, properties=properties)

    // TODO: should this be T?
    final override fun <T:LanguageNode?>targetsAs(label:String?, keys:List<String>?, properties:Map<String,Any>?): T? =
        r(direction= SelectDirection.RIGHT, label=label, keys=keys, properties=properties).n().firstAs()

    final override fun <T:LanguageNode>targetsOrMakeAs(relationshipLabel:String, makeTargetLabel:String, makeTargetKey:String?, keys:List<String>?, properties:Map<String,Any>?): T {
        r(direction= SelectDirection.RIGHT, label=relationshipLabel, keys=keys, properties=properties).n().firstAs<T>() ?.let{
            return it
        }
        context.make<T>(makeTargetLabel, makeTargetKey ?: autoKey()).let {
            it.mergeMe()
            this.relate(relationshipLabel, it)
            return it
        }
    }


    fun relate(relationshipLabel: String, targetKey:String, properties: Map<String, Any?> = mapOf()) {
        Relationship(
            relationshipLabel=relationshipLabel,
            source_key = key, target_key = targetKey, properties = properties, context = context,
        ).createMe()
    }

    fun relate(relationshipLabel: String, targetNode:LanguageNode, properties: Map<String, Any?> = mapOf()) {
        // TODO: implement relate
        relate(relationshipLabel, targetNode.key, properties)
    }

}

// ===========================================================================================================

open class Relationship(
    key:String = autoKey(),
    var source_key: String?, // TODO: this is wonky... really, key string should be required
    var target_key: String?, // ditto
    properties: Map<String, Any?> = mapOf(),
    context:ContextInterface = LocalContext,
    relationshipLabel: String = "RELATES_TO"
): LanguageRelationship, Item(key, properties, context) {

    // TODO: elvis operator defaulting to empty string is wonky here... should just always have the key
    // and TODO maybe: should these be read upfront when initialized?
    override val source: Node by lazy { Node(source_key ?: "") }
    override val target: Node by lazy { Node(target_key ?: "") }

    override val label = LocalContext.getLabel(relationshipLabel) { k, p, c -> Relationship(k, null, null, p, c) }

}

fun relate(sourceKey:String, relationshipLabel:String, targetKey:String,
           properties: Map<String, Any?> = mapOf(), context:ContextInterface = LocalContext) {
    Relationship(
        relationshipLabel=relationshipLabel,
        source_key = sourceKey, target_key = targetKey, properties = properties, context = context,
    ).createMe()
}

fun relate(sourceKey:String, labelsToKeys:Map<String, String>, context:ContextInterface = LocalContext) {
    labelsToKeys.forEach { (label, key) ->
        relate(sourceKey, label, key, context=context)
    }
}




