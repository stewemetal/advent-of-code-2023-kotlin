fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { game ->
            val (gameName, draws) = game.split(":")
            val gameId = gameName.split(" ")[1].toInt()

            val isGameValid = draws.trim().split(";", ",").all { drawRaw ->
                val (countRaw, color) = drawRaw.trim().split(" ")
                val count = countRaw.toInt()
                color == "red" && count <= 12 ||
                        color == "green" && count <= 13 ||
                        color == "blue" && count <= 14
            }
            if (isGameValid) {
                result += gameId
            }
        }
        return result
    }

    fun powerOfMaxDrawsPerColor(draws: String): Int =
            draws.trim().split(";", ",").map { drawRaw ->
                val (countRaw, color) = drawRaw.trim().split(" ")
                Pair(color, countRaw.toInt())
            }.groupBy { it.first }
                    .map { entry ->
                        entry.value.maxBy { it.second }
                    }.fold(0) { aggregate, currentValue ->
                        if (aggregate == 0) {
                            currentValue.second
                        } else {
                            aggregate * currentValue.second
                        }
                    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { result, game ->
            val (_, draws) = game.split(":")

            result + powerOfMaxDrawsPerColor(draws)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
