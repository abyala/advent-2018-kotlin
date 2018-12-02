package com.abyala.advent.day2

import com.abyala.advent.readFileAsListOfStrings


fun calculateChecksum(boxIDs: List<String>): Int {
    val letterCounts: List<LetterCount> = boxIDs.asSequence()
            .map { box -> box.toList().groupBy { it } }
            .map { LetterCount(it.hasValueOfSize(2), it.hasValueOfSize(3)) }
            .toList()

    return letterCounts.count { it.hasDouble } * letterCounts.count { it.hasTriple }
}

private fun Map<Char, List<Char>>.hasValueOfSize(size: Int) = any { it.value.size == size }

private class LetterCount(val hasDouble: Boolean, val hasTriple: Boolean)

fun main(args: Array<String>) {
    val filename = args[0]
    println("Reading file $filename")
    val input = readFileAsListOfStrings(filename)
    println("The checksum is ${calculateChecksum(input)}")
}