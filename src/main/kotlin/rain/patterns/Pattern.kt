package rain.patterns

import rain.interfaces.*

// a node that represents an iterable over a group nodes ... each of which is connected
// to this node, in a "pattern"
interface Pattern: LanguageNode {

    val isAlter: Boolean

    val isLeaf: Boolean

    val branches: SelectInterface

    val leaves: SelectInterface

    val nodes: SelectInterface

    // set to an instance of CuePath if this node is created in the context of a TreeSelect
    var cuePath: CuePath?

    // TODO: testing!
    //TODO: below assumes that all ancestor properties should carry down... are we sure that's what we want?
    val propertiesUp: Map<String, Any?> get() = properties + cuePath?.properties.orEmpty()

    fun <T>getUp(name:String):T = propertiesUp[name] as T

    fun saveDown() {
        nodes.forEach { save() }
    }

    fun <T>vein(name: String): Sequence<T> = leaves.asSequence().map { it.properties[key] as T }

    // TODO: does this work???
    fun setVein(name: String, vararg values:Any) {
        leaves.asSequence().zip(values.asSequence()).forEach { it.first[key] = it.second }
    }

    // TODO: implement
    // abstract val parents: SelectInterface

    // TODO: implement the below
//    # TODO: assume this doesn't need to be serialized?
//    leaf_hooks: Iterable[Callable[["rain.Pattern", "rain.Pattern"], "rain.Pattern"]] = ()
//    vein_hooks: Iterable[Callable[["rain.Pattern", Any, int], Any]] = ()
//    _parentage = ()
//    # TODO: MAYBE consider this
//    # node_hooks: Iterable[Callable[["rain.Pattern", "rain.Pattern"], "rain.Pattern"]] = ()
//
}
