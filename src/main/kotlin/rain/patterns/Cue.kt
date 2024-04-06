package rain.patterns

import rain.interfaces.*
import rain.language.*

// TODO... long and nasty with all the class inheritance and companion objects ... REFACTOR!!!!

open class Cue(
    key:String = rain.utils.autoKey(),
    properties: Map<String, Any?> = mapOf(),
    context: ContextInterface = LocalContext,
): Node(key, properties, context) {

    override val label = LocalContext.getLabel("Cue") { k, p, c -> Cue(k, p, c) }

    // TODO: these all need tests!
    // also TODO: should these be by lazy?

    fun <T:Tree>cuesTree() = r(SelectDirection.RIGHT, "CUES").n<T>().first

    fun <T:Tree>cuesNextTree() = r(SelectDirection.RIGHT, "CUES_NEXT").n<Cue>().r(SelectDirection.RIGHT, "CUES").n<T>().first

    fun <T:Tree>cuesPrevTree() = r(SelectDirection.LEFT, "CUES_NEXT").n<Cue>().r(SelectDirection.RIGHT, "CUES").n<T>().first


//    # # TO CONSIDER: would this be used?
//    # if alter_node := self.altered_by:
//    #     return alter_node.alter(pattern)
//    # else:
//    #     return pattern
//
//    # # TO CONSIDER: would this be used?
//    # @property
//    # def altered_by(self) -> Tuple["rain.AlterCue"]:
//    #     return tuple(self.r("<-", "ALTERS").n())
}


// ===========================================================================================================
// ===========================================================================================================

// TODO: maybe these aren't needed as separate classes?

open class Contains(
    key:String = rain.utils.autoKey(),
    source_key: String?, // TODO: this is wonky... really, key string should be required
    target_key: String?, // ditto
    properties: Map<String, Any?> = mapOf(),
    context:ContextInterface = LocalContext,
): Relationship(key, source_key, target_key, properties, context) {

    override val label = LocalContext.getLabel("CONTAINS") { k, p, c -> Contains(k, null, null, p, c) }
}

// ===========================================================================================================

open class Cues(
    key:String = rain.utils.autoKey(),
    source_key: String?, // TODO: this is wonky... really, key string should be required
    target_key: String?, // ditto
    properties: Map<String, Any?> = mapOf(),
    context:ContextInterface = LocalContext,
): Relationship(key, source_key, target_key, properties, context) {

    override val label = LocalContext.getLabel("CUES") { k, p, c -> Cues(k, null, null, p, c) }
}

// ===========================================================================================================

open class CuesNext(
    key:String = rain.utils.autoKey(),
    source_key: String?, // TODO: this is wonky... really, key string should be required
    target_key: String?, // ditto
    properties: Map<String, Any?> = mapOf(),
    context:ContextInterface = LocalContext,
): Relationship(key, source_key, target_key, properties, context) {

    override val label = LocalContext.getLabel("CUES_NEXT") { k, p, c -> CuesNext(k, null, null, p, c) }
}

// ===========================================================================================================

open class CuesFirst(
    key:String = rain.utils.autoKey(),
    source_key: String?, // TODO: this is wonky... really, key string should be required
    target_key: String?, // ditto
    properties: Map<String, Any?> = mapOf(),
    context:ContextInterface = LocalContext,
): Relationship(key, source_key, target_key, properties, context) {

    override val label = LocalContext.getLabel("CUES_FIRST") { k, p, c -> CuesFirst(k, null, null, p, c) }
}

// ===========================================================================================================

open class CuesLast(
    key:String = rain.utils.autoKey(),
    source_key: String?, // TODO: this is wonky... really, key string should be required
    target_key: String?, // ditto
    properties: Map<String, Any?> = mapOf(),
    context:ContextInterface = LocalContext,
): Relationship(key, source_key, target_key, properties, context) {

    override val label = LocalContext.getLabel("CUES_LAST") { k, p, c -> CuesLast(k, null, null, p, c) }


}


