package com.abyala.advent

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day5Tests {

    @Test
    fun `Part 1 simple tests`() {
        assertEquals(0, Day5("aA").problem1())
        assertEquals(0, Day5("abBA").problem1())
        assertEquals(4, Day5("abAB").problem1())
        assertEquals(6, Day5("aabAAB").problem1())
        assertEquals(10, Day5("dabAcCaCBAcCcaDA").problem1())
    }

    @Test
    fun `Part 1 real data`() {
        assertEquals(11152, getRealData().problem1())
    }

    @Test
    fun `Part 2 simple test`() {
        assertEquals(4, Day5("dabAcCaCBAcCcaDA").problem2())
    }

    @Test
    fun `Part 2 real data`() {
        assertEquals(6136, getRealData().problem2())
    }

    private fun getRealData() = Day5(File("src/main/resources/day5.txt").readText())
}