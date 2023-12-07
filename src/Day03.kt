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
        val numbers = mutableListOf<Number>()
        val gears = mutableListOf<Gear>()
        input.forEachIndexed { rowIndex, row ->
            var numberStart = -1
            var currentNumber = ""
            row.forEachIndexed { index, char ->
                when {
                    char.isDigit() -> {
                        currentNumber += char
                        if (numberStart == -1) numberStart = index
                    }

                    else -> {
                        if (char == '*') {
                            gears.add(Gear(rowIndex, index))
                        }
                        if (currentNumber.isNotEmpty()) {
                            numbers.add(
                                    Number(
                                            value = currentNumber.toInt(),
                                            row = rowIndex,
                                            startIndex = numberStart,
                                            endIndex = index - 1,
                                    )
                            )
                            currentNumber = ""
                            numberStart = -1
                        }
                    }
                }
            }
            if (currentNumber.isNotEmpty()) {
                numbers.add(
                        Number(
                                value = currentNumber.toInt(),
                                row = rowIndex,
                                startIndex = numberStart,
                                endIndex = row.lastIndex,
                        )
                )
            }
        }

        val gearRatioPairs = mutableListOf<Pair<Int, Int>>()
        gears.forEach { gear ->
            val gearNumbers = numbers.filter { number ->
                (number.row == gear.row && (number.endIndex == gear.column - 1 || number.startIndex == gear.column + 1)) ||
                        (number.row in gear.row - 1..gear.row + 1 && gear.column in number.startIndex - 1..number.endIndex + 1)
            }
            if (gearNumbers.size == 2) {
                gearRatioPairs.add(Pair(gearNumbers[0].value, gearNumbers[1].value))
            }
        }

        return gearRatioPairs.sumOf {
            it.first * it.second
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)
    part2(testInput)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

data class Number(
        val value: Int,
        val row: Int,
        val startIndex: Int,
        val endIndex: Int,
)

data class Gear(
        val row: Int,
        val column: Int,
)
