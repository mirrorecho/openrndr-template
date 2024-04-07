package rain.interfaces

import org.openrndr.color.ColorRGBa

enum class SelectDirection(val shortForm: String) {
    RIGHT("->"),
    LEFT("<-"),
    RIGHT_NODE("->()"),
    LEFT_NODE("<-()"),
}

// TODO maybe: include an enume for itemType? (NODE vs RELATIONSHIP)
