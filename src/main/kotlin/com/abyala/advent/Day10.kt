package com.abyala.advent

class Day10(input: List<String>) {
    private val initialPoints = input.map { PointOfLight.parse(it) }

    // Seriously, who would make all of these default values available on a public method?
    // It's possible in Kotlin, so I'll do it here.  But this should be a delegated private method for sure.
    tailrec fun calculateMessage(currentList: List<PointOfLight> = initialPoints,
                                 nextList: List<PointOfLight> = initialPoints,
                                 currentMinMaxX: Pair<Int, Int> = Pair(0, 0),
                                 currentMinMaxY: Pair<Int, Int> = Pair(0, 0),
                                 currentSize: Long = Long.MAX_VALUE,
                                 iterationCount: Int = -1): TimedResult {
        val minMaxX = nextList.asSequence().map { it.x }.run { Pair(min()!!, max()!!) }
        val minMaxY = nextList.asSequence().map { it.y }.run { Pair(min()!!, max()!!) }
        val nextSize = minMaxX.difference() * minMaxY.difference()

        return if (currentSize < nextSize) {
            // This is the best we're going to get
            TimedResult(graphToString(currentList, currentMinMaxX.first, currentMinMaxX.second, currentMinMaxY.first, currentMinMaxY.second), iterationCount)
        } else {
            calculateMessage(nextList, nextList.map { it.move() }, minMaxX, minMaxY, nextSize, iterationCount + 1)
        }
    }

    private fun Pair<Int, Int>.difference() = this.second.toLong() - this.first.toLong()

    private fun graphToString(points: List<PointOfLight>, minX: Int, maxX: Int, minY: Int, maxY: Int): List<String> {
        val pointSet = points.asSequence().map { Pair(it.x, it.y) }.toSet()
        return (minY..maxY).map { y ->
            (minX..maxX).joinToString("") { x ->
                if (pointSet.contains(Pair(x, y))) "#" else "."
            }
        }
    }

    data class PointOfLight(val x: Int, val y: Int, val velocityX: Int, val velocityY: Int) {
        fun move(): PointOfLight = PointOfLight(x + velocityX, y + velocityY, velocityX, velocityY)

        companion object {
            private val pattern = """position=<\s*(.*),\s*(.*)> velocity=<\s*(.*),\s*(.*)>""".toRegex()
            fun parse(line: String): PointOfLight =
                    pattern.find(line)?.groupValues?.let {
                        PointOfLight(it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt())
                    } ?: throw IllegalArgumentException("Invalid input line: $line")

        }
    }

    class TimedResult(val lines: List<String>, val iterationCount: Int)
}