package rain.utils

import kotlin.math.abs

fun autoKey(): String {
    // TODO: consider a better implementation for this?
    return  abs((100..999999999999).random()).toString()
}

// slick way to create infinite cycle:
// https://stackoverflow.com/questions/48007311/how-do-i-infinitely-repeat-a-sequence-in-kotlin
fun <T> Sequence<T>.cycle() = sequence { while (true) yieldAll(this@cycle) }

fun <T>cycleOf(vararg elements:T): Sequence<T> = elements.asSequence().cycle()

// copies map1 into a new map, then puts all values from map2 into that map as well, and returns the result
// TODO: used? Just able to use simple addition???!!
fun mapCopy(map1: Map<String, Any?>, map2: Map<String, Any?>): Map<String, Any?> = map1.toMutableMap().apply { putAll(map2) }
