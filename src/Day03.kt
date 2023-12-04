fun main() {
    fun String.areSymbolsInSubstring(startOffset: Int, endOffset: Int) =
            substring((startOffset - 1).coerceAtLeast(0), (endOffset + 2).coerceAtMost(lastIndex + 1))
                    .any { character ->
                        !character.isDigit() && character != '.'
                    }

    fun isPartNumber(startOffset: Int, row: String, endOffset: Int, input: List<String>, rowIndex: Int) =
            // Check the character before the number
            (startOffset != 0 && row[startOffset - 1] != '.') ||
                    // Check the character after the number
                    (endOffset != row.lastIndex && row[endOffset + 1] != '.') ||
                    // Check the row above the current one for symbols next to the number
                    input.getOrNull(rowIndex - 1)?.run {
                        areSymbolsInSubstring(startOffset, endOffset)
                    } ?: false ||
                    // Check the row below the current one for symbols next to the number
                    input.getOrNull(rowIndex + 1)?.run {
                        areSymbolsInSubstring(startOffset, endOffset)
                    } ?: false

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { rowIndex, row ->
            var startOffset = 0
            while (startOffset <= row.lastIndex) {
                if (!row[startOffset].isDigit()) {
                    startOffset++
                } else {
                    var endOffset = startOffset + 1
                    while (endOffset <= row.lastIndex && row[endOffset].isDigit()) {
                        endOffset++
                    }
                    endOffset--

                    if (isPartNumber(startOffset, row, endOffset, input, rowIndex)) {
                        val number = row.substring(startOffset, endOffset + 1).toInt()
                        sum += number
                    }

                    startOffset = endOffset + 1
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
//    part2(input).println()
}
