package com.abyala.advent

import java.math.BigInteger

class Day9(private val numPlayers: Int, private val numMarbles: Int) {
    private var currentNode = Node(0)
    private val players = mutableMapOf<Int, BigInteger>().apply {
        (0 until numPlayers).forEach { put(it, BigInteger.ZERO) }
    }

    fun problem1(): BigInteger {
        (1..numMarbles).forEach { addMarble(it) }
        return players.maxBy { it.value }!!.value
    }

    private fun addMarble(marble: Int) {
        when {
            marble.rem(23) == 0 -> twentyThreeInsert(marble)
            else -> normalInsert(marble)
        }
    }

    private fun normalInsert(marble: Int) {
        currentNode = (currentNode.right.insert(marble))
    }

    private fun twentyThreeInsert(marble: Int) {
        val player = marble % numPlayers
        (1 until 7).forEach { currentNode = currentNode.left }
        players[player] = players[player]!! + marble.toBigInteger() + currentNode.removeLeft().toBigInteger()
    }

    // Why would I be so foolish as to make my own linked list? Because I want a pointer to the current value.
    class Node(private val value: Int) {
        var left: Node = this
        var right: Node = this

        fun insert(newValue: Int): Node = Node(newValue).also {
            val oldRight = right
            right = it
            oldRight.left = it
            it.left = this
            it.right = oldRight
        }

        fun removeLeft(): Int {
            val oldLeft = left
            oldLeft.left.right = this
            this.left = oldLeft.left
            return oldLeft.value
        }
    }
}