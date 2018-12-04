package com.abyala.advent

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day3Tests {

    @Test
    fun `test the parser`() {
        assertEquals(Claim(123, 1, 3, 4, 4), "#123 @ 1,3: 4x4".toClaim())
    }

    @Test
    fun `test a Claim with a single point`() {
        val points = Claim(555, 2, 3, 1, 1).points
        assertEquals(1, points.size)
        assertEquals(Pair(2, 3), points.first())
    }

    @Test
    fun `test a 2x3 grid`() {
        val points = Claim(555, 10, 11, 2, 3).points
        setOf(Pair(10, 11), Pair(10, 12), Pair(10, 13), Pair(11, 11), Pair(11, 12), Pair(11, 13)).forEach {
            assertTrue(points.contains(it), "Should have had point $it")
        }
    }

    @Test
    fun `simple overlap example`() {
        val inputs = sequenceOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")
        assertEquals(4, Day3().countOverlaps(inputs))
    }

    @Test
    fun `real overlap test`() {
        val inputs = File("src/main/resources/day3.txt").bufferedReader().lineSequence()
        assertEquals(110389, Day3().countOverlaps(inputs))
    }

    @Test
    fun `simple isolated example`() {
        val inputs = sequenceOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")
        assertEquals(3, Day3().findLoner(inputs))
    }

    @Test
    fun `real isolated example`() {
        val inputs = File("src/main/resources/day3.txt").bufferedReader().lineSequence()
        assertEquals(552, Day3().findLoner(inputs))
    }
}