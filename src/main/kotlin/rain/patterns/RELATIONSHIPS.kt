package rain.patterns

import rain.language.*

object RELATIONSHIPS {

    val CUES_FIRST = RelationshipLabel("CUES_FIRST")
    val CUES_LAST = RelationshipLabel("CUES_LAST")
    val CUES_NEXT = RelationshipLabel("CUES_NEXT")
    val CONTAINS = RelationshipLabel("CONTAINS")
    val CUES = RelationshipLabel("CUES")
}

val RelationshipCompanion.patterns get() = RELATIONSHIPS

