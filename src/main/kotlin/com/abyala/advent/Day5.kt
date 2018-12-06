package com.abyala.advent

import kotlin.math.max

class Day5(private val input: String) {
    fun problem1(): Int = input.removeAdjacentDups().length
    fun problem2(): Int = input.uniqueLetters().asSequence()
            .map { input.replace(it.toString(), "", true) }
            .map { it.removeAdjacentDups().length }
            .minBy { it }!!

    // Yay -- my first actual tail-recursive Kotlin function!
    private tailrec fun String.removeAdjacentDups(startingIndex: Int = 0): String {
        val index = (max(startingIndex, 0) until length - 1)
                .firstOrNull { isNextCharOpposite(it) }
        return if (index == null) this else removeIndexAndNext(index).removeAdjacentDups(index - 1)
    }

    private fun String.isNextCharOpposite(index: Int): Boolean = length > 1 && get(index).isOppositeCase(get(index + 1))
    private fun String.boundedSubstring(startIndex: Int, endIndex: Int = length): String = when {
        endIndex < 0 || startIndex >= length -> ""
        else -> substring(startIndex, endIndex)
    }

    private fun String.removeIndexAndNext(index: Int): String = boundedSubstring(0, index) + boundedSubstring(index + 2)
    private fun String.uniqueLetters(): Set<Char> = toUpperCase().groupBy { it }.keys

    private fun Char.isOppositeCase(c: Char) = this != c && this.equals(c, true)
}

