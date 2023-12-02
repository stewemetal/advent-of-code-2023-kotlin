import java.lang.Integer.sum

fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0) { sum, currentLine ->
            val firstNumber = currentLine.find { it.digitToIntOrNull() != null }?.digitToInt() ?: 0
            val lastNumber = currentLine.findLast { it.digitToIntOrNull() != null }?.digitToInt() ?: 0
            sum + "$firstNumber$lastNumber".toInt()
        }
    }

    val numbers = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
    )

    fun part2(input: List<String>): Int {
        return input.fold(0) { sum, currentLine ->
            var firstNumber: Int = 0
            var lastNumber: Int = 0

            var firstFoundIndex = Int.MAX_VALUE
            var lastFoundIndex = -1

            val possibleNumberStringsInLine = numbers.filter {
                currentLine.contains(it.key)
            }

            possibleNumberStringsInLine.forEach { numberEntry ->
                val firstIndex = currentLine.indexOf(numberEntry.key)
                val lastIndex = currentLine.lastIndexOf(numberEntry.key)
                if(firstIndex < firstFoundIndex) {
                    firstFoundIndex = firstIndex
                    firstNumber = numberEntry.value
                }
                if(lastIndex > lastFoundIndex) {
                    lastFoundIndex = lastIndex
                    lastNumber = numberEntry.value
                }
            }

            val firstNumberIndex = currentLine.indexOfFirst { it.digitToIntOrNull() != null }
            val lastNumberIndex = currentLine.indexOfLast { it.digitToIntOrNull() != null }
            if (firstNumberIndex != -1 && firstNumberIndex < firstFoundIndex) {
                firstNumber = currentLine[firstNumberIndex].digitToInt()
            }
            if (lastNumberIndex != -1 && lastNumberIndex > lastFoundIndex) {
                lastNumber = currentLine[lastNumberIndex].digitToInt()
            }

            sum + "$firstNumber$lastNumber".toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
