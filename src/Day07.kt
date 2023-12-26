import java.lang.Exception

fun main() {
    fun part1(input: List<String>): Int =
            input.readHands()
                    .sorted()
                    .mapIndexed { index, hand ->
                        (index + 1) * hand.bid
                    }
                    .sum()

    fun part2(input: List<String>): Int {
        var sum = 0
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 0)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

private val cardStrengths = mapOf(
        'A' to 'a',
        'K' to 'b',
        'Q' to 'c',
        'J' to 'd',
        'T' to 'e',
        '9' to 'f',
        '8' to 'g',
        '7' to 'h',
        '6' to 'i',
        '5' to 'j',
        '4' to 'k',
        '3' to 'l',
        '2' to 'm',
)

private enum class HandType(val strength: Int) {
    HIGH_CARD(0),
    ONE_PAIR(1),
    TWO_PAIR(2),
    THREE_OF_A_KIND(3),
    FULL_HOUSE(4),
    FOUR_OF_A_KIND(5),
    FIVE_OF_A_KIND(6)
}

private data class Hand(
        val cards: String,
        val bid: Int,
        val type: HandType,
) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        return if (type.strength != other.type.strength) {
            type.strength.compareTo(other.type.strength)
        } else {
            other.mapCardsToStrengths().compareTo(mapCardsToStrengths())
        }
    }

    private fun Hand.mapCardsToStrengths() =
            cards.map { it.cardStrength() }.joinToString()

    private fun Char.cardStrength() = cardStrengths.getValue(this)
}

private fun List<String>.readHands() =
        map {
            val (hand, bid) = it.split(" ")
            Hand(
                    cards = hand,
                    bid = bid.toInt(),
                    type = hand.toHandType()
            )
        }

private fun String.toHandType(): HandType {

    fun String.isFiveOfAKind(): Boolean =
            groupBy { it }.run {
                size == 1 && any { it.value.size == 5 }
            }

    fun String.isFourOfAKind(): Boolean =
            groupBy { it }.run {
                size == 2 && any { it.value.size == 4 }
            }

    fun String.isFullHouse(): Boolean =
            groupBy { it }.run {
                size == 2 && any { it.value.size == 3 } && any { it.value.size == 2 }
            }

    fun String.isThreeOfAKind(): Boolean =
            groupBy { it }.run {
                size == 3 && any { it.value.size == 3 }
            }

    fun String.isTwoPair(): Boolean =
            groupBy { it }.run {
                size == 3 && count { it.value.size == 2 } == 2
            }

    fun String.isOnePair(): Boolean =
            groupBy { it }.run {
                size == 4 && any { it.value.size == 2 }
            }

    fun String.isHighCard(): Boolean =
            groupBy { it }.size == 5

    return when {
        isHighCard() -> HandType.HIGH_CARD
        isOnePair() -> HandType.ONE_PAIR
        isTwoPair() -> HandType.TWO_PAIR
        isThreeOfAKind() -> HandType.THREE_OF_A_KIND
        isFullHouse() -> HandType.FULL_HOUSE
        isFourOfAKind() -> HandType.FOUR_OF_A_KIND
        isFiveOfAKind() -> HandType.FIVE_OF_A_KIND
        else -> throw Exception("Invalid hand type: $this")
    }
}
