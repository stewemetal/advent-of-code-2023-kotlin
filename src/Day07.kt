import HandType.FIVE_OF_A_KIND
import java.lang.Exception

fun main() {
    fun part1(input: List<String>): Int =
            input.readHands()
                    .sortedWith(
                            HandComparator(
                                    mapOf(
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
                                    ),
                            ),
                    )
                    .mapIndexed { index, hand ->
                        (index + 1) * hand.bid
                    }
                    .sum()

    fun part2(input: List<String>): Int =
            input.readHandsWithJokers()
                    .sortedWith(
                            HandComparator(
                                    mapOf(
                                            'A' to 'a',
                                            'K' to 'b',
                                            'Q' to 'c',
                                            'T' to 'd',
                                            '9' to 'e',
                                            '8' to 'f',
                                            '7' to 'g',
                                            '6' to 'h',
                                            '5' to 'i',
                                            '4' to 'j',
                                            '3' to 'k',
                                            '2' to 'l',
                                            'J' to 'm',
                                    ),
                            ),
                    )
                    .mapIndexed { index, hand ->
                        (index + 1) * hand.bid
                    }
                    .sum()


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

private class HandComparator(
        val cardStrengths: Map<Char, Char>,
) : Comparator<Hand> {

    override fun compare(o1: Hand, o2: Hand): Int {
        return if (o1.type.strength != o2.type.strength) {
            o1.type.strength.compareTo(o2.type.strength)
        } else {
            o2.mapCardsToStrengths().compareTo(o1.mapCardsToStrengths())
        }
    }

    private fun Hand.mapCardsToStrengths() =
            cards.map { it.asCardStrength() }.joinToString()

    private fun Char.asCardStrength() = cardStrengths.getValue(this)
}

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
)

private fun List<String>.readHands() =
        map {
            val (hand, bid) = it.split(" ")
            Hand(
                    cards = hand,
                    bid = bid.toInt(),
                    type = hand.toHandType()
            )
        }

private fun List<String>.readHandsWithJokers() =
        map {
            val (hand, bid) = it.split(" ")
            Hand(
                    cards = hand,
                    bid = bid.toInt(),
                    type = hand.toHandTypeWithJokers()
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
        isFiveOfAKind() -> FIVE_OF_A_KIND
        else -> throw Exception("Invalid hand type: $this")
    }
}

private fun String.toHandTypeWithJokers(): HandType {

    val cardWithMostCountWithoutJoker = groupBy { it }
            .toList()
            .filter { it.first != 'J' }
            .sortedByDescending { it.second.size }.getOrNull(0)
            ?.first

    return if (cardWithMostCountWithoutJoker != null) {
        this.replace('J', cardWithMostCountWithoutJoker).toHandType()
    } else {
        FIVE_OF_A_KIND
    }
}
