package rain.patterns._bak

import rain.patterns.nodes.*

// HAD CONSIDERED: maybe this should just a live on the Cue itself?
// OR MAYBE NOT, because that doesn't take into account the ancestors
// WHERE SHOULD THIS LIVE????

// TODO.. rethink how this is used?

//class CuePath(
//    val cue: Cue, // needed? (probably not, but also probably simplifies things
//    // TODO: which of the following make more sense?
//    val ancestors: List<Pattern>, // trees or alters
////    val ancestorCues: List<Cue> // needed?
//) {
//
//    // TODO: these should all be selects instead of lists!
//
//
//    val properties: Map<String, Any?> by lazy {
//        parent?.properties.orEmpty() + parent?.cuePath?.properties.orEmpty()
//    }
//
//
//}