package rain.patterns.nodes

import rain.language.*
import rain.patterns.relationships.*


// TODO... long and nasty with all the class inheritance and companion objects ... REFACTOR!!!!

open class Cue(
    key:String = rain.utils.autoKey(),
): Node(key) {
    companion object : NodeLabel<Cue>(Cue::class, Node, { k -> Cue(k) })
    override val label: NodeLabel<out Cue> = Cue

    // TODO: these all need tests!
    // also TODO: should these be by lazy?

    // TODO: bring back if needed...
//    fun <T: Tree>cues(label:NodeLabel<T>) = r(CUES).n(label).first

    // TODO maybe: bring back if used
//    fun <T:Tree>cuesNextTree() = r(SelectDirection.RIGHT, "CUES_NEXT").n<Cue>().r(SelectDirection.RIGHT, "CUES").n<T>().first
//
//    fun <T:Tree>cuesPrevTree() = r(SelectDirection.LEFT, "CUES_NEXT").n<Cue>().r(SelectDirection.RIGHT, "CUES").n<T>().first


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


