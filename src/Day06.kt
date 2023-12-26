fun main() {
    fun part1(input: List<String>): Int =
            input
                    .readRaces()
                    .map { race ->
                        race.getNumberOfWinningCases()
                    }.reduce { accumulator, current ->
                        accumulator * current
                    }

    fun part2(input: List<String>): Int {
        var sum = 0
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 0)
    part2(testInput)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

private data class Race(
        val time: Int,
        val distance: Int,
)

private fun List<String>.readRaces(): List<Race> {
    val whitespacesRegex = "\\s+".toRegex()
    val times = this[0].split(":\\s+".toRegex())[1].split(whitespacesRegex).map { it.toInt() }
    val distances = this[1].split(":\\s+".toRegex())[1].split(whitespacesRegex).map { it.toInt() }
    return buildList {
        times.forEachIndexed { index, time ->
            add(Race(time, distances[index]))
        }
    }
}

private fun Race.getNumberOfWinningCases(): Int {
    var numberOfCases = 0
    (1 until time).forEach { buttonHoldingTime ->
        if ((time - buttonHoldingTime) * buttonHoldingTime > distance) {
            numberOfCases++
        }
    }
    return numberOfCases
}


 
