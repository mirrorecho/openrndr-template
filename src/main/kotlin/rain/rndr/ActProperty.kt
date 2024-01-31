package rain.rndr
//
//open class ActProperty<T>(
//    var name:String,
//    val parentAct: Act,
//    var relationshipLabel: String? = null
//    ) {
//
//
//    open val relationshipNode: rain.language.Node by lazy { parentAct.rndrMachine.targetsAs(relationshipLabel) as rain.language.Node }
//
//    open fun getValue(): T {
//        return if (relationshipLabel == null) {
//            parentAct.properties[name] as T
//        } else {
//            parentAct.relatedMachinesToActs[relationshipLabel]!!.properties[name] as T
//        }
//    }
//
//}
//
//
//
//// TODO: maybe this shouldn't inherit from MachineProperty? Fundamentally different?
//// TODO: is this neven needed?
//open class ComboProperty<T>(
//    name:String,
//    parentAct: Act,
//    relationshipLabel: String? = null,
//): ActProperty<T>(name, parentAct, relationshipLabel) {
//
//    val relationshipNodeAs: T
//        get() = relationshipNode as T
//
//    // TODO: how to create an instance of T here? Even possible?
//    override fun getValue(): T {
//        throw Exception("getValue not possible for combo properties. Use node instance instead.")
//        //        return act.properties[name] as T
//    }
//}