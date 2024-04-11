package rain.language

import rain.interfaces.*
import rain.patterns.relationships.*

fun <T:Node>Sequence<T>.select():SelectNodes =
    SelectNodes(keys = this.toList().map { it.key } )

fun <T:Relationship>Sequence<T>.select(labelName: String):SelectRelationships =
    SelectRelationships(keys = this.toList().map { it.key }, labelName = labelName )

val Sequence<Node>.select:SelectFromNodes<Node> get() =
    SelectFromNodes(this)

fun select(
    vararg keys:String,
    properties:Map<String,Any>?=null,
    labelName: String? = null,
) = SelectNodes(keys.asList(), properties, labelName)

open class SelectNodes(
    override val keys: List<String>? = null,
    override val properties: Map<String, Any?>? = null,
    override val labelName: String? = null,
    override var selectFrom: SelectInterface? = null,
    ):SelectInterface, NodeSelectable {

    override val direction: SelectDirection? = null

    // TODO maybe: get based on keys?
//    operator fun get(vararg vals:String):List<String> {
//        println(vals.toList())
//    }

    // TODO: Test!!!
    operator fun get(vararg selects:SelectNodes):SelectNodes {
        return selects.asIterable().zipWithNext { s, sNext ->
            sNext.apply { selectFrom = s }
        }.last()
    }

    operator fun <T:Node>invoke(label: NodeLabelInterface<T>):Sequence<T> = label.context.graph.selectNodes(this, label)

    fun indexOfFirst(key:String): Int = this(Node.label).indexOfFirst {it.key==key}

    fun contains(key: String): Boolean = this.indexOfFirst(key) > -1

    fun <T:Node>first(label:NodeLabelInterface<T>): T? = this(label).firstOrNull()

    // TODO: yay, caching! Maybe make use of something like this?
//    val <T:Node>cachedItems: List<T> by lazy { this().toList() }
//
//    private var _cachedFirst: T? = null
//
//    var cachedFirst: T? get() {
//        _cachedFirst?.let { return it }
//        _cachedFirst = first
//        return _cachedFirst
//    }
//        set(value) {
//            _cachedFirst = value
//        }


    open fun r(
        vararg keys: String,
        properties: Map<String, Any>? = null,
        labelName: String,
        direction: SelectDirection = SelectDirection.RIGHT,
    ): SelectRelationships {
        return SelectRelationships(
            keys.asList(),
            properties,
            labelName,
            direction,
            this,
        )
    }
}

// ===========================================================================================================

// TODO.. how to handle this ???!!!!!

open class SelectFromNodes<T:Node>(
    val nodes: Sequence<T>
): SelectNodes() {
    override val keys by lazy { nodes.toList().map { it.key } }
}

// ===========================================================================================================

open class SelectRelationships(
    override val keys: List<String>? = null,
    override val properties: Map<String, Any?>? = null,
    override val labelName: String,
    override val direction: SelectDirection? = null,
    override val selectFrom: SelectInterface? = null,
):SelectInterface {

    // TODO: yay, caching! Maybe make use of this?
//    override val cachedItems: List<Relationship> by lazy { asSequence().toList() }

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
// TODO: bring back if needed ...
//// ===========================================================================================================
//
//open class SelectSelf<T:Node>(
//    private val node:T,
//):SelectNodes<Node>(
//    node.label,
//    listOf(node.key)
//) {
//    override fun asSequence() = sequence { yield(this@SelectSelf.node) }
//}

// TODO: bring back if needed ...
//// ===========================================================================================================
//
//open class SelectEmpty<T:Node>(
//    label: NodeLabel<out T>
//):SelectNodes<T>(label) {
//    override fun asSequence(): Sequence<T> = sequenceOf()
//}

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
