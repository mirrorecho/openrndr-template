package rain.patterns

import rain.interfaces.*
import rain.language.*

// TODO... long and nasty with all the class inheritance and companion objects ... REFACTOR!!!!

open class Cue(
    key:String = rain.utils.autoKey(),
): Node(key) {
    companion object : NodeCompanion<Cue>(Node.childLabel { k -> Cue(k) })
    override val label: NodeLabel<out Cue> = Cue.label

    // TODO: these all need tests!
    // also TODO: should these be by lazy?

    fun <T:Tree>cuesTree(label:NodeLabel<T>) = r(Relationship.patterns.CUES).n(label).first

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


