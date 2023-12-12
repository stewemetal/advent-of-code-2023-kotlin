import kotlin.math.pow

fun main() {
    fun String.extractNumbers(): List<Int> =
            trim().split("\\s+".toRegex()).map { it.toInt() }

    fun part1(input: List<String>): Int {
        return input.fold(0) { sum, line ->
            val (_, rawCardNumbers, rawWinningNumbers) = line.split(" | ", ": ")
            val cardNumbers = rawCardNumbers.extractNumbers()
            val winningNumbers = rawWinningNumbers.extractNumbers()


            val numbersMatching = cardNumbers.intersect(winningNumbers.toSet()).size
            if (numbersMatching > 0) {
                sum + 2.0.pow(numbersMatching - 1).toInt()
            } else {
                sum
            }
        }
    }

    fun part2(input: List<String>): Int {
        val scratchcards = mutableMapOf<Int, Int>()
        val scratchcardPile = mutableMapOf<Int, Int>()
        input.forEach { line ->
            val (rawCardId, rawCardNumbers, rawWinningNumbers) = line.split(" | ", ": ")
            val cardId = rawCardId.split("\\s+".toRegex())[1].toInt()
            val cardNumbers = rawCardNumbers.extractNumbers()
            val winningNumbers = rawWinningNumbers.extractNumbers()

            val numbersMatching = cardNumbers.intersect(winningNumbers.toSet()).size

            scratchcards[cardId] = numbersMatching
            scratchcardPile[cardId] = 1
        }

        scratchcards.forEach { (cardId, value) ->
            repeat(scratchcardPile.getValue(cardId)) {
                repeat(value) { current ->
                    val wonCardId = cardId + current + 1
                    scratchcardPile[wonCardId] = (scratchcardPile[wonCardId] ?: 0) + 1
                }
            }
        }

        return scratchcardPile.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
