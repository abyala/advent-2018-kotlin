package com.abyala.advent

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day7Tests {
    @Test
    fun `First example question`() {
        assertEquals("CABDFE", Day7(getFirstSampleData()).problem1())
    }

    @Test
    fun `First question`() {
        assertEquals("GNJOCHKSWTFMXLYDZABIREPVUQ", getRealData().problem1())
    }

    @Test
    fun `Second example question`() {
        assertEquals(15, Day7(getFirstSampleData(), 0).simpleScheduler(2))
    }

    @Test
    fun `Second question`() {
        assertEquals(886, getRealData().problem2())
    }

    private fun getFirstSampleData(): List<String> = """
Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin.""".trimIndent().lines()

    private fun getRealData() = Day7(File("src/main/resources/day7.txt").readText().lines())
}