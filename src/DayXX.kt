fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)
    part2(testInput)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
