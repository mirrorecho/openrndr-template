package rain.language

import rain.interfaces.*
import rain.rndr.rndr

import rain.utils.autoKey
import kotlin.reflect.KMutableProperty0


// ===========================================================================================================

abstract class Item(
    override val key:String,
): LanguageItem {

    final override val properties: MutableMap<String, Any?> = mutableMapOf()

    // TODO: worth keeping the fancy properties?? KISS!!!

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
): LanguageNode, Item(key) {
    companion object : NodeCompanion<Node>( rootLabel {k->Node(k) } )
    override val label:NodeLabel<out Node> = Node.label

    val selectSelf by lazy { SelectSelf(this) }

    protected open val autoCreateTargets:List<KMutableProperty0<out CachedTarget<out Node>>> = listOf()

    fun autoTarget() {
        autoCreateTargets.forEach {
            it.get().createIfMissing()
        }
    }

    fun <T:Node>cachedTarget(rLabel: RelationshipLabel, nLabel: NodeLabel<T>) =
        CachedTarget(this, rLabel, nLabel)

    fun r(
        label:RelationshipLabel,
        keys:List<String>?=null,
        properties:Map<String,Any>?=null,
        direction: SelectDirection = SelectDirection.RIGHT,
    ): SelectRelationships =
        selectSelf.r(
            label,
            keys,
            properties,
            direction
        )

    fun <T:Node>targets(
        rLabel:RelationshipLabel, // TODO: add default label
        nLabel:NodeLabel<T>,
    ): T? = r(
        rLabel,
        direction = SelectDirection.RIGHT
    ).n(nLabel).first

    fun <T:Node>targetsOrMake(
        rLabel:RelationshipLabel, // TODO: add default label
        nLabel:NodeLabel<T>,
        targetKey: String = autoKey()
    ): T {
        targets(rLabel, nLabel)?.let { return it }
        return nLabel.merge(targetKey).also { this.relate(rLabel, it) }
    }

    // TODO: maybe implement this...?
//    fun invoke()

    fun relate(
        rLabel:RelationshipLabel, // TODO: add default label
        targetKey:String,
    ) = rLabel.create(key, targetKey)

    fun relate(
        rLabel:RelationshipLabel, // TODO: add default label
        targetNode:Node,
    ) = rLabel.create(key, targetNode.key)

}

// ===========================================================================================================

// just for fiddling around purposes...
open class SpecialNode(
    key:String = autoKey(),
): Node(key) {
    companion object : NodeCompanion<SpecialNode>(Node.childLabel { k -> SpecialNode(k) })
    override val label: NodeLabel<out SpecialNode> = SpecialNode.label
}

// ===========================================================================================================

open class RelationshipCompanion {
    val TARGETS = RelationshipLabel("TARGETS")
}

open class Relationship(
    key:String = autoKey(),
    override val label: RelationshipLabel,
    var sourceKey: String,
    var targetKey: String,
): LanguageRelationship, Item(key) {
    companion object : RelationshipCompanion()

    // TODO maybe: elvis operator defaulting to empty string is wonky here... should just always have the key
    // and TODO maybe: should these be read upfront when initialized?
    override val source: Node by lazy { Node(sourceKey ?: "") }
    override val target: Node by lazy { Node(targetKey ?: "") }

}

fun relate(
    sourceKey: String,
    rLabel:RelationshipLabel, // TODO: add default label
    targetKey:String,
) = rLabel.create(sourceKey, targetKey)


// TODO: would this be used?
//fun relate(sourceKey:String, labelsToKeys:Map<RelationshipLabel, String>, context:ContextInterface = LocalContext) {
//    labelsToKeys.forEach { (label, key) ->
//        relate(sourceKey, label, key)
//    }
//}

// TODO: move somewhere else
class CachedTarget<T:Node>(
    val sourceNode:Node,
    val rLabel: RelationshipLabel,
    val nLabel: NodeLabel<T>,
) {
    val rQuery = sourceNode.r(rLabel)
    val query = rQuery.n(nLabel)
    private var _cachedValue:T? = query.first

    var target: T? get() {
        _cachedValue?.let { return it }
        _cachedValue = query.first
        return _cachedValue
    }
        set(value) {
            _cachedValue = value
            rQuery.first?.delete()
            value?.let { sourceNode.relate(rLabel, it) }
        }

    fun createIfMissing() {
        if (_cachedValue==null) {
            _cachedValue = nLabel.create().also { sourceNode.relate(rLabel, it) }
        }
    }

}

