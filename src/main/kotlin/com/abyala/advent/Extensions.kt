package com.abyala.advent

import java.io.File

fun readFileAsListOfStrings(filename: String): List<String> =
        File(filename).readLines()
