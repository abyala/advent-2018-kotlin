package com.abyala.advent

class Day7(instructions: List<String>, private val baseCostPerStep: Int = 60) {

    private val dependencyObjects: Set<Dependency> =
            instructions.map { word ->
                PATTERN.find(word)!!.groupValues.let {
                    Pair(it[1], it[2])
                }
            }.fold(mutableMapOf<String, Dependency>()) { result, (independent, dependent) ->
                result.apply {
                    putIfAbsent(independent, Dependency(independent))
                    putIfAbsent(dependent, Dependency(dependent))
                    get(independent)!!.outbound += dependent
                    get(dependent)!!.inbound += independent
                }
            }.values.toSet()

    fun problem1(): String = orderWork().asSequence().map { it.letter }.joinToString(separator = "")
    fun problem2(): Int = simpleScheduler(5)

    private tailrec fun orderWork(order: List<Dependency> = emptyList(),
                                  usedLetters: Set<String> = emptySet(),
                                  dependencies: Set<Dependency> = dependencyObjects): List<Dependency> {
        val next = findNextAvailableWork(dependencies, usedLetters)
        return if (next == null) order else orderWork(order + next, usedLetters + next.letter, dependencies - next)

    }

    fun simpleScheduler(numWorkers: Int): Int {
        val workRemaining = dependencyObjects.toMutableSet()
        val currentWork = mutableMapOf<String, Int>()
        val lettersComplete = mutableSetOf<String>()

        for (timeIndex in generateSequence(0) { it + 1 }) {
            // Clear up any completed work
            val finished = currentWork.filterValues { it == timeIndex }.keys
            lettersComplete += finished
            currentWork -= lettersComplete

            assignNewWork(workRemaining, currentWork, lettersComplete, numWorkers, timeIndex)

            if (currentWork.isEmpty()) {
                return timeIndex
            }
        }
        throw IllegalArgumentException("Cannot finish the work?!?")
    }

    private fun assignNewWork(workRemaining: MutableSet<Dependency>,
                              currentWork: MutableMap<String, Int>,
                              lettersComplete: MutableSet<String>,
                              numWorkers: Int,
                              timeIndex: Int) {
        while (currentWork.size < numWorkers) {
            findNextAvailableWork(workRemaining, lettersComplete)?.let {
                workRemaining -= it
                currentWork += it.letter to (timeIndex + it.letter.computationCost())
            } ?: return
        }
    }

    private fun findNextAvailableWork(dependencies: Collection<Dependency>,
                                      lettersComplete: Collection<String>): Dependency? =
            dependencies.asSequence()
                    .filter { it.hasAllDependenciesMet(lettersComplete) }
                    .sortedBy { it.letter }
                    .firstOrNull()

    private fun String.computationCost(): Int = baseCostPerStep + (get(0) - 'A' + 1)

    data class Dependency(val letter: String,
                          val inbound: MutableSet<String> = mutableSetOf(),
                          val outbound: MutableSet<String> = mutableSetOf()) {
        fun hasAllDependenciesMet(letters: Collection<String>) = letters.containsAll(inbound)
    }

    companion object {
        private val PATTERN = """Step (\w+) must be finished before step (\w+) can begin.""".toRegex()
    }
}