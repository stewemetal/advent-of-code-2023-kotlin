fun main() {
    fun part1(input: List<String>): Long =
            input
                    .readRaces()
                    .map { race ->
                        race.getNumberOfWinningCases()
                    }.reduce { accumulator, current ->
                        accumulator * current
                    }

    fun part2(input: List<String>): Long =
            input
                    .readRace()
                    .getNumberOfWinningCases()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)
    part2(testInput)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

private data class Race(
        val time: Long,
        val distance: Long,
)

private fun List<String>.readRaces(): List<Race> {
    val whitespacesRegex = "\\s+".toRegex()
    val times = this[0].split(":\\s+".toRegex())[1].split(whitespacesRegex).map { it.toLong() }
    val distances = this[1].split(":\\s+".toRegex())[1].split(whitespacesRegex).map { it.toLong() }
    return buildList {
        times.forEachIndexed { index, time ->
            add(Race(time, distances[index]))
        }
    }
}

private fun List<String>.readRace(): Race {
    val whitespacesRegex = "\\s+".toRegex()
    val time = this[0].split(":\\s+".toRegex())[1].replace(whitespacesRegex, "").toLong()
    val distance = this[1].split(":\\s+".toRegex())[1].replace(whitespacesRegex, "").toLong()
    return Race(time, distance)
}

private fun Race.getNumberOfWinningCases(): Long {
    var numberOfCases = 0L
    (1 until time).forEach { buttonHoldingTime ->
        if ((time - buttonHoldingTime) * buttonHoldingTime > distance) {
            numberOfCases++
        }
    }
    return numberOfCases
}


 
