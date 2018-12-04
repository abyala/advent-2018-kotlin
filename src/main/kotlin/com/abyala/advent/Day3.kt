package com.abyala.advent

import com.abyala.advent.Day3.Companion.REGEX

typealias Point = Pair<Int, Int>

class Day3 {
    /*
        MutableMap?  Mutability is much better than waiting for ever for the code to respond.
     */
    fun countOverlaps(claims: Sequence<String>): Int =
            claims.map(String::toClaim)
                    .map(Claim::points)
                    .flatten()
                    .fold(mutableMapOf<Point, Int>()) { map, point ->
                        map[point] = map[point]?.plus(1) ?: 1
                        map
                    }.count { it.value > 1 }

    fun findLoner(claims: Sequence<String>): Int {
        val allClaims = claims.map(String::toClaim).toSet()
        return allClaims.first { claim ->
            allClaims.all { claim.id == it.id || claim.points.intersect(it.points).isEmpty() }
        }.id
    }

    companion object {
        val REGEX = "#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)".toRegex()
    }
}

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int) {
    val points: Set<Point> by lazy {
        (0 until width).flatMap { x ->
            (0 until height).map { Point(left + x, top + it) }
        }.toSet()
    }
}

fun String.toClaim(): Claim =
        REGEX.find(this)?.groupValues?.let {
            Claim(it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt(), it[5].toInt())
        } ?: throw RuntimeException("Invalid input: $this")