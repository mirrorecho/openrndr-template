package rain.rndr

//import rain.utils.*
//import org.openrndr.Program
//import org.openrndr.color.ColorHSVa
//import org.openrndr.color.ColorRGBa
//import org.openrndr.math.Vector2
//import rain.interfaces.ContextInterface
//import rain.language.LocalContext
//import rain.language.Relationship
//import rain.machines.Machine
//import rain.patterns.Leaf
//
//open class NodeOrPropertyValue(
//    // represents a Double value that can be:
//    // (1) get/set via this reference node's own properties
//    // or (2) get/set via a relationship to another node
//    // ... and for either (1) or (2) above, can either be:
//    // (a) fixed
//    // or (b) animated
//    val referenceNode: rain.language.Node,
//    val name:String,
//    val targetRelationshipLabel: String? = null,
//    var defaultValue: Double = 0.0,
//) {
//    val context = referenceNode.context
//
//    fun getValue(opName: String? = null) {
//
//    }
//}
//
//
//
//
//// TODO: or is this an interface?
//// TODO: can MachineFuncs have multiple instances? (assume YES)
//// MachineFunc is a factory for Act ...
//open class MachineFunc(
//    key:String = autoKey(),
//    properties: Map<String, Any?> = mapOf(),
//    context: ContextInterface = LocalContext,
//): Machine, Leaf(key, properties, context) { // TODO: is Leaf the best parent class?
//
//    override val label = LocalContext.getLabel("MachineFunc", "Machine", "Leaf") { k, p, c -> MachineFunc(k, p, c) }
//
//    // TODO maybe: lambdas the best idea here?
//    open var startAct: (act: Act) -> Unit = {}
//    open var updateAct: (update: Update)-> Unit = {} // TODO: even necessary? or should this be defined in Update?
//    open var renderAct: (act: Act)->Unit = {}
////    open var stopAct: (act: Act)-> Unit = {} // assume not needed (logic in Update)
//
//    fun getOrUpdateAct(score:Score, name:String= autoKey(), actProperties: Map<String, Any?>) : Act {
//
//        return Act(
//            name,
//            this,
//            properties.toMutableMap().apply { putAll(actProperties) },
//            score
//            // TODO: implement map of machine relationships to acts
//        )
//    }
//
//    // TODO: below methods erroneous?
//    fun actProperty(act: Act, name:String) = act.properties[name]
//    fun <T>actPropertyAs(act: Act, name:String) = act.properties[name] as T
//
//    // TODO maybe: acts management with dur (i.e. kill op when dur is up)
//
//    // TODO: helper method(s) to create this along with necessary relationships/nodes
//    // (and do the same for specifics below)
//
//}
//
//// TODO: ANIMATE THE OP!!!!!!
//open class ValueFunc(
//    key:String = autoKey(),
//    value: Double = 0.0, // TODO maybe: consider whether type should be Double? with default null
//    context: ContextInterface = LocalContext,
//): MachineFunc(key, mapOf("value" to value), context) {
//    override val label = LocalContext.getLabel("ValueFunc", "MachineFunc", "Machine", "Leaf") { k, p, c -> MachineFunc(k, p, c) }
//
//    var value: Double by this.properties
//
//    // TODO: below methods erroneous??
//    fun actVal(act: Act): Double {
//        return this.actPropertyAs(act, "value")
//    }
//
//    fun relatedActVal(act: Act, relationshipName: String): Double {
//        return this.actVal(act.relatedAct(relationshipName))
//    }
//
//}
