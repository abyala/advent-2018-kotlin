package com.abyala.advent

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day9Tests {
    @Test
    fun `Test sample 1 data`() {
        assertEquals(8317.toBigInteger(), Day9(10, 1618).problem1())
        assertEquals(146373.toBigInteger(), Day9(13, 7999).problem1())
        assertEquals(2764.toBigInteger(), Day9(17, 1104).problem1())
        assertEquals(54718.toBigInteger(), Day9(21, 6111).problem1())
        assertEquals(37305.toBigInteger(), Day9(30, 5807).problem1())
    }

    @Test
    fun `Test real 1 data`() {
        assertEquals(425688.toBigInteger(), getRealData().problem1())
    }

    @Test
    fun `Test real 2 data`() {
        assertEquals(3526561003.toBigInteger(), Day9(411, 7117000).problem1())
    }

    private fun getRealData() = Day9(411, 71170)
}