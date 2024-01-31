package rain.patterns

// HAD CONSIDERED: maybe this should just a live on the Cue itself?
// OR MAYBE NOT, because that doesn't take into account the ancestors
// WHERE SHOULD THIS LIVE????

// TODO.. rethink how this is used?

class CuePath(
    val cue: Cue, // needed? (probably not, but also probably simplifies things
    // TODO: which of the following make more sense?
    val ancestors: List<Pattern>, // trees or alters
//    val ancestorCues: List<Cue> // needed?
) {

    // TODO: maybe these should all be selects instead of lists?

    val aunts = listOf<Pattern>()
    val preceding = listOf<Pattern>()
    val following = listOf<Pattern>()
    val siblings = listOf<Pattern>()

    val parent: Pattern? = ancestors.lastOrNull()
    val root: Pattern? = ancestors.firstOrNull()
    val previous: Pattern? = null
    val next: Pattern? = null

    val properties: MutableMap<String, Any?> by lazy {
        val returnMap = parent?.properties.orEmpty().toMutableMap()
        parent?.cuePath?.let { returnMap.putAll(it.properties) }
        returnMap
    }


}