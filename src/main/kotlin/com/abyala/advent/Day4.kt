package com.abyala.advent

import java.time.LocalDate
import java.time.LocalDateTime

class Day4(lines: List<String>) {
    private val guards: Map<GuardId, Guard> = ShiftParser.parse(lines)

    fun strategy1(): Int {
        val guard = guards.maxBy { it.value.totalMinutesAsleep() }!!.value
        val sleepiestMinute = guard.sleepiestMinute()

        return guard.guardId * sleepiestMinute
    }

    fun strategy2(): Int {
        val result = guards
                .values
                .associateBy { it.minuteMostOftenAsleep() }
                .maxBy { it.key.second }!!

        val guardId = result.value.guardId
        val minute = result.key.first

        return guardId * minute
    }
}

typealias GuardId = Int
typealias LineParser = (String) -> LineDef?

class ParseStatus(var lastGuardId: GuardId = -1, var lastSleepTime: LocalDateTime = LocalDateTime.now())

object ShiftParser {

    fun parse(lines: List<String>): Map<GuardId, Guard> {
        val guards = mutableMapOf<GuardId, Guard>()
        val parseStatus = ParseStatus()

        val lineDefs = lines.map { line ->
            parsers.asSequence().mapNotNull { it.invoke(line) }.first()
        }.sortedBy { it.timestamp }

        lineDefs.forEach {
            when (it) {
                is LineDef.NewGuard -> parseStatus.lastGuardId = it.guardId
                is LineDef.FallAsleep -> parseStatus.lastSleepTime = it.timestamp
                is LineDef.WakeUp -> parseStatus.run {
                    guards.computeIfAbsent(lastGuardId) { id ->
                        Guard(id)
                    }.shifts += Shift(lastGuardId, lastSleepTime.toLocalDate(), lastSleepTime.minute, it.timestamp.minute)
                }
            }
        }

        return guards
    }

    private const val DATE_PATTERN = """\[(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})\]"""
    private val NEW_GUARD_PATTERN = """$DATE_PATTERN Guard #(\d+) begins shift""".toRegex()
    private val FALL_ASLEEP_PATTERN = """$DATE_PATTERN falls asleep""".toRegex()
    private val WAKE_UP_PATTERN = """$DATE_PATTERN wakes up""".toRegex()


    private val parsers: List<LineParser> =
            listOf(
                    { line ->
                        NEW_GUARD_PATTERN.find(line)?.groupValues?.let {
                            LineDef.NewGuard(it[6].toInt(), LocalDateTime.of(it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt(), it[5].toInt()))
                        }
                    },
                    { line ->
                        FALL_ASLEEP_PATTERN.find(line)?.groupValues?.let {
                            LineDef.FallAsleep(LocalDateTime.of(it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt(), it[5].toInt()))
                        }
                    },
                    { line ->
                        WAKE_UP_PATTERN.find(line)?.groupValues?.let {
                            LineDef.WakeUp(LocalDateTime.of(it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt(), it[5].toInt()))
                        }
                    })

}

sealed class LineDef(val timestamp: LocalDateTime) {
    class NewGuard(val guardId: GuardId, timestamp: LocalDateTime) : LineDef(timestamp) {
        override fun toString(): String = "New Guard $guardId at $timestamp"
    }

    class FallAsleep(timestamp: LocalDateTime) : LineDef(timestamp) {
        override fun toString() = "Fall Asleep at $timestamp"
    }

    class WakeUp(timestamp: LocalDateTime) : LineDef(timestamp) {
        override fun toString() = "Wake up at $timestamp"
    }
}

data class Guard(val guardId: Int, val shifts: MutableSet<Shift> = mutableSetOf()) {
    fun everyMinuteAsleep(): Sequence<Int> = shifts.asSequence().flatMap { IntRange(it.start, it.end - 1).asSequence() }
    fun totalMinutesAsleep(): Int = everyMinuteAsleep().count()
    fun sleepiestMinute(): Int =
            everyMinuteAsleep().groupingBy { it }.eachCount().maxBy { it.value }!!.key

    fun minuteMostOftenAsleep(): Pair<Int, Int> = everyMinuteAsleep().groupingBy { it }.eachCount().maxBy { it.value }!!.toPair()
}

data class Shift(val guardId: Int, val date: LocalDate, val start: Int, val end: Int)