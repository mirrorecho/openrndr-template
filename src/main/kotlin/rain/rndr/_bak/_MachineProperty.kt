package rain.rndr._bak

//open class MachineProperty<T>(
//    var name:String,
//    var relationshipLabel: String? = null
//    ) {
//
//    var parentMachine: MachineFunc? = null // TODO: is this nullable property wonky here??
//
//    open val relationshipNode: rain.language.Node by lazy { parentMachine!!.targetsAs(relationshipLabel) as rain.language.Node }
//
//    open fun getValue(act:Act): T {
//        return if (relationshipLabel == null || parentMachine==null) {
//            act.properties[name] as T
//        } else {
//            act.relatedMachinesToActs[parentMachine!!.key]!!.properties[name] as T
//        }
//
//    }
//}
//
//
//
//// TODO: maybe this shouldn't inherit from MachineProperty? Fundamentally different?
//open class ComboMachineProperty<T:MachineFunc>(
//    name:String,
//    relationshipLabel: String, // note that a related node will be required ... could get confusing and nasty
//): MachineProperty<T>(name, relationshipLabel) {
//
//    val relationshipNodeAs: T
//        get() = relationshipNode as T
//
//    // TODO: how to create an instance of T here? Even possible?
//    override fun getValue(act:Act): T {
//        throw Exception("getValue not possible for combo properties. Use node instance instead.")
//        //        return act.properties[name] as T
//    }
//}