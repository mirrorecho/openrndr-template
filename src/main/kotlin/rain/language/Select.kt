package rain.language

import rain.interfaces.*

// TODO: maybe use generic type parameters
open class SelectNodes<T:Node>(
    override val label: NodeLabel<out T>,
    override val keys: List<String>? = null,
    override val properties: Map<String, Any?>? = null,
    override val direction: SelectDirection? = null,
    override val selectFrom: SelectInterface<*>? = null,
    ):SelectInterface<T> {


    // TODO: yay, caching! Make use of this!
    override val cachedItems: List<T> by lazy { asSequence().toList() }

    private var _cachedFirst: T? = null

    var cachedFirst: T? get() {
        _cachedFirst?.let { return it }
        _cachedFirst = first
        return _cachedFirst
    }
        set(value) {
            _cachedFirst = value
        }

    override fun asSequence(): Sequence<T> = sequence {
        yieldAll(this@SelectNodes.graph.selectNodes(this@SelectNodes))
    }

    // TODO: bring this back if it seems useful
//    operator fun invoke(keys:List<String>?, properties:Map<String,Any>?): SelectNodes<T> {
//        // TODO: should direction be set to this.direction? (warrants more thought and testing)
//        return SelectNodes(context=this.context, label=label, keys=keys, properties=properties, selectFrom=this)
//    }

    open fun r(
        label: RelationshipLabel,
        keys: List<String>? = null,
        properties: Map<String, Any>? = null,
        direction: SelectDirection = SelectDirection.RIGHT,
    ): SelectRelationships {
        return SelectRelationships(
            label,
            keys,
            properties,
            direction,
            this,
        )
    }
}

// ===========================================================================================================

open class SelectRelationships(
    override val label: RelationshipLabel,
    override val keys: List<String>? = null,
    override val properties: Map<String, Any?>? = null,
    override val direction: SelectDirection? = null,
    override val selectFrom: SelectInterface<*>? = null,
):SelectInterface<Relationship> {

    // TODO: yay, caching! Make use of this!
    override val cachedItems: List<Relationship> by lazy { asSequence().toList() }

    override fun asSequence(): Sequence<Relationship> = sequence {
        yieldAll(this@SelectRelationships.graph.selectRelationships(this@SelectRelationships ))
    }

    fun <NT:Node>n(
        label:NodeLabel<NT>,
        keys:List<String>?=null,
        properties:Map<String,Any>?=null
    ):SelectNodes<NT> {
        return SelectNodes(
            label,
            keys,
            properties,
            when (this.direction) {
                SelectDirection.RIGHT -> SelectDirection.RIGHT_NODE
                SelectDirection.LEFT -> SelectDirection.LEFT_NODE
                else -> null
            },
            this,
        )
    }

}

// ===========================================================================================================

open class SelectSelf(
    private val node:Node,
    // TODO maybe: also pass Label into Select constructor? (what performs better?)
):SelectNodes<Node>(
    node.label,
    listOf(node.key)
) {

    override fun asSequence() = sequence { yield(this@SelectSelf.node) }

}

// ===========================================================================================================

open class SelectEmpty(
):SelectNodes<Node>(Node.label) {
    override fun asSequence(): Sequence<Node> = sequenceOf()

}

// ===========================================================================================================

// NOTE: implementation possibility below with type parameter on the class itself... something to consider

//open class Select<T:Item>(
//    override val context:ContextInterface = LocalContext,
//    override val label: String? = null,
//    override val keys: List<String>? = null,
//    override val properties: Map<String, Any?>? = null,
//    override val selectFrom: SelectInterface? = null,
//    override val direction: SelectDirection? = null,
//):SelectInterface {
//
//    override fun get(key:String) {throw NotImplementedError()}
//
//    override operator fun invoke(label:String?, keys:List<String>?, properties:Map<String,Any>?): Select<T> {
//        // TODO: should direction be set to this.direction? (warrants more thought and testing)
//        return Select(context=this.context, label=label, keys=keys, properties=properties, selectFrom=this)
//    }
//
//    open fun <R:Relationship>rt(direction:SelectDirection, label:String?, keys:List<String>?, properties:Map<String,Any>?):TargetedRelationshipSelect<R> {
//        return TargetedRelationshipSelect(context=this.context, label=label, keys=keys, properties=properties, selectFrom=this, direction=direction)
//    }
//
//    // TODO any way to avoid this duplicative implementation with default Type
//    open fun r(direction:SelectDirection, label:String?, keys:List<String>?, properties:Map<String,Any>?):TargetedRelationshipSelect<Relationship> {
//        return this.rt(direction=direction, label=label, keys=keys, properties=properties)
//    }
//
//    open fun <N:Node>nt(label:String?, keys:List<String>?, properties:Map<String,Any>?):Select<N> {
//        throw NotImplementedError()
//    }
//
//    // TODO ditto, any way to avoid this duplicative implementation with default Type
//    open fun n(label:String?, keys:List<String>?, properties:Map<String,Any>?):Select<Node> {
//        return this.nt(label=label, keys=keys, properties=properties)
//    }
//}
//
//class TargetedRelationshipSelect<R:Relationship>(
//    context:ContextInterface = LocalContext,
//    label: String? = null,
//    keys: List<String>? = null,
//    properties: Map<String, Any?>? = null,
//    selectFrom: SelectInterface? = null,
//    direction: SelectDirection? = null,
//):Select<R>(context, label, keys, properties, selectFrom, direction) {
//    // TODO: document
//    // TODO: consider testing for supported directions (-> and <- only)
//
//    override fun <R:Relationship>rt(direction:SelectDirection, label:String?, keys:List<String>?, properties:Map<String,Any>?):TargetedRelationshipSelect<R> {
//        throw NotImplementedError("Chaining .r relationship selects not supported (add a .n select between)")
//    }
//
//    override fun <N:Node>nt(label:String?, keys:List<String>?, properties:Map<String,Any>?):Select<N> {
//        return Select(context=this.context, label=label, keys=keys, properties=properties, selectFrom=this)
//    }
//
//}
