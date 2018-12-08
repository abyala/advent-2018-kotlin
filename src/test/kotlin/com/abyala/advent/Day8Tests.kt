package com.abyala.advent

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day8Tests {
    @Test
    fun `Test parser`() {
        val day = getTestData()
        checkNode(day.root, "A", 2, listOf(1, 1, 2))
        checkNode(day.root.children[0], "B", 0, listOf(10, 11, 12))
        checkNode(day.root.children[1], "C", 1, listOf(2))
        checkNode(day.root.children[1].children[0], "D", 0, listOf(99))
    }

    private fun checkNode(node: Day8.Node, name: String, numChildren: Int, metadata: List<Int>) {
        assertEquals(numChildren, node.children.size, "Incorrect number of children for node $name")
        assertEquals(metadata, node.metadata, "Incorrect metadata for node $name")
    }

    @Test
    fun `Test sample 1 data`() {
        assertEquals(138, getTestData().problem1())
    }

    @Test
    fun `Test actual 1 data`() {
        assertEquals(47464, getRealData().problem1())
    }

    @Test
    fun `Test sample 2 data`() {
        assertEquals(66, getTestData().problem2())
    }

    @Test
    fun `Test actual 2 data`() {
        assertEquals(23054, getRealData().problem2())
    }

    private fun getTestData() = Day8("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")
    private fun getRealData() = Day8(File("src/main/resources/day8.txt").readText())
}