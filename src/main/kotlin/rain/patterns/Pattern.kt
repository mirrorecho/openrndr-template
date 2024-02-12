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

// ===========================================================================================================

// TODO: maybe rethink naming?
interface CellPattern:Pattern {

    var simultaneous: Boolean

    val veins: Sequence<MutableMap<String, Any?>>

    // TODO: naming?
    // TOD: testing!
    val propertiesWithHeritage: Map<String, Any?>
        get() {
            //TODO: below assumes that all ancestor properties should carry down... are we sure that's what we want?
            return this.properties.toMutableMap().apply { this.putAll( cuePath?.properties.orEmpty() ) }
        }

    fun <T>propertyByVein(key: String): Sequence<T> = veins.map { it[key] as T }

    // TODO: implement something like this for setting PARTIAL (or longer?) lists of values
//    fun setPropertyByVein(key: String, values:Sequence<Any>) {
//        veins.zip(values).forEach { it.first[key] = it.second }
//    }

    // TODO: would be ideal to set defaults to these
    // and TODO: how to handle lambdas?

    // TODO: look into Kotlin Duration, also look into how OPENRNDR handles time
    var dur:Sequence<Double>
        get() = propertyByVein("dur")
        set(values) { throw NotImplementedError() }

    // the following probably not even needed since dur.sum() is so easy!
//    val sumDur: Double get() = dur.sum()

    var machine:Sequence<String?>
        get() = propertyByVein("machine")
        set(values) { throw NotImplementedError() }
}