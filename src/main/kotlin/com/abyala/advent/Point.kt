package com.abyala.advent

class Point(val x: Int, val y: Int) {

    operator fun minus(other: Point): Int = Math.abs(x - other.x) + Math.abs(y - other.y)

    infix fun inPerimiter(graph: Graph): Boolean = (x == 0 || x == graph.width - 1 || y == 0 || y == graph.height - 1)
}

class Graph(val width: Int, val height: Int) {
    val allPoints: List<Point> by lazy {
        (0 until width).flatMap { x ->
            (0 until height).map { y ->
                Point(x, y)
            }
        }
    }
}