package com.abyala.advent

import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate
import kotlin.test.assertEquals

class Day4Tests {

    @Test
    fun `shift parser`() {
        val input = """
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

        val guards = ShiftParser.parse(input.lines())
        assertEquals(2, guards.size)
        assertEquals(2, guards[10]?.shifts?.filter { it.date.dayOfMonth == 1 }?.size)

        assertEquals(240, Day4(input.lines()).strategy1())
    }

    @Test
    fun `real strategy 1 data`() {
        assertEquals(67558, Day4(File("src/main/resources/day4.txt").readLines()).strategy1())
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
}