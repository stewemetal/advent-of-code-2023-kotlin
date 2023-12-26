fun main() {
    fun part1(input: List<String>): Int =
            input
                    .readDesertMap()
                    .followDirections()

    fun part2(input: List<String>): Int {
        var sum = 0
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day08_test_1")
    val testInput2 = readInput("Day08_test_2")
    check(part1(testInput1) == 2)
    check(part1(testInput2) == 6)
    check(part2(testInput1) == 0)
    check(part2(testInput2) == 0)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

private fun DesertMap.followDirections(): Int {
    var currentNode: String = "AAA"
    var stepsTaken: Int = 0
    var directionIndex = 0
    while (currentNode != "ZZZ") {
        val direction = directions[directionIndex]
        val node = nodes.getValue(currentNode)
        when (direction) {
            'L' -> currentNode = node.first
            'R' -> currentNode = node.second
        }
        stepsTaken++
        directionIndex++
        directionIndex %= directions.length
    }

    return stepsTaken
}

private data class DesertMap(
        val directions: String,
        val nodes: Map<String, Pair<String, String>>,
)

private fun List<String>.readDesertMap(): DesertMap {
    val directions = get(0)
    val nodeDescriptions = drop(2)
    val nodes = mutableMapOf<String, Pair<String, String>>()

    nodeDescriptions.forEach { nodeDescription ->
        val (nodeName, nodeConnections) = nodeDescription.split(" = ")
        val (left, right) = nodeConnections.trim('(', ')').split(", ")
        nodes[nodeName] = Pair(left, right)
    }

    return DesertMap(
            directions = directions,
            nodes = nodes,
    )
}
