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

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
