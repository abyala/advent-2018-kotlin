package com.abyala.advent

class Day8(input: String) {
    val root: Node = Node.parse(input)

    fun problem1(): Int = root.sumOfMetadata()
    fun problem2(): Int = root.complexValue

    data class Node(val children: List<Node>, val metadata: List<Int>) {

        val complexValue: Int by lazy {
            if (children.isEmpty()) {
                sumOfMetadata()
            } else {
                metadata.asSequence().map {
                    if (it <= children.size) children[it - 1].complexValue else 0
                }.sum()
            }
        }

        fun sumOfMetadata(): Int = metadata.sum() + children.asSequence().map { it.sumOfMetadata() }.sum()

        companion object {
            fun parse(str: String): Node = parse(str.split(" ").iterator())
            private fun parse(values: Iterator<String>): Node {
                val numChildren = values.nextInt()
                val numMetadata = values.nextInt()

                return Node(
                        (0 until numChildren).map {
                            parse(values)
                        },
                        (0 until numMetadata).map {
                            values.nextInt()
                        })
            }

            private fun Iterator<String>.nextInt() = next().toInt()
        }
    }
}