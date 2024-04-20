package rain.interfaces

import org.openrndr.color.ColorRGBa

enum class SelectDirection(val shortForm: String) {
    RIGHT("->"),
    LEFT("<-"),
    CONCAT("<-"),
    NONE("")
//    RIGHT_NODE("->()"),
//    LEFT_NODE("<-()"),
}

// TODO maybe: include an enum for itemType? (NODE vs RELATIONSHIP)
