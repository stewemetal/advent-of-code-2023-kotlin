import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0) { sum, line ->
            val (_, rawCardNumbers, rawWinningNumbers) = line.split(" | ", ": ")
            val cardNumbers = rawCardNumbers.trim().split("\\s+".toRegex()).map { it.toInt() }
            val winningNumbers = rawWinningNumbers.trim().split("\\s+".toRegex()).map { it.toInt() }


            val numbersMatching = cardNumbers.intersect(winningNumbers.toSet()).size
            if (numbersMatching > 0) {
                sum + 2.0.pow(numbersMatching - 1).toInt()
            } else {
                sum
            }
        }
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 0)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
