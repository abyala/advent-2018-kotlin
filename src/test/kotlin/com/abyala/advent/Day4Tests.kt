package com.abyala.advent

import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate
import kotlin.test.assertEquals

class Day4Tests {

    @Test
    fun `Test the shift parser`() {
        val guards = ShiftParser.parse(SAMPLE_INPUT.lines())
        assertEquals(2, guards.size)
        assertEquals(2, guards[10]?.shifts?.filter { it.date.dayOfMonth == 1 }?.size)
    }

    @Test
    fun `Test strategy 1 sample data`() {
        assertEquals(240, getSampleData().strategy1())
    }

    @Test
    fun `Real strategy 1 data`() {
        assertEquals(67558, getRealData().strategy1())
    }

    @Test
    fun `Test guard calculations`() {
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)

        val guard = Guard(123)
        guard.shifts += Shift(123, today, 5, 9)
        guard.shifts += Shift(123, today, 10, 12)
        guard.shifts += Shift(123, tomorrow, 8, 9)

        assertEquals(7, guard.totalMinutesAsleep())
        assertEquals(7, guard.everyMinuteAsleep().count())
        assertEquals(8, guard.sleepiestMinute())
    }

    @Test
    fun `Test strategy 2 sample data`() {
        assertEquals(4455, getSampleData().strategy2())
    }

    @Test
    fun `Real strategy 2 data`() {
        assertEquals(78990, getRealData().strategy2())
    }

    private fun getSampleData() = Day4(SAMPLE_INPUT.lines())
    private fun getRealData() = Day4(File("src/main/resources/day4.txt").readLines())

    companion object {
        val SAMPLE_INPUT = """
[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up
        """.trimIndent()
    }
}