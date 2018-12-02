package com.abyala.advent.day2

import com.abyala.advent.readFileAsListOfStrings


fun findPrototypeFabric(boxIDs: List<String>): String {
    val allCombos = boxIDs.flatMap { box ->
        boxIDs.map { Pair(box, it) }
    }
    return allCombos
            .filterNot { it.first == it.second }
            .map { it.first.offByOne(it.second) }
            .filterNotNull()
            .first()
}

private fun String.offByOne(other: String): String? {
    val matchingChars: String = toCharArray().zip(other.toCharArray())
            .filter { it.first == it.second }
            .map { it.first }
            .joinToString(separator = "")

    return if (matchingChars.length == length - 1) matchingChars else null
}

fun main(args: Array<String>) {
    val filename = args[0]
    println("Reading file $filename")
    val input = readFileAsListOfStrings(filename)
    println("The prototype is ${findPrototypeFabric(input)}")
}