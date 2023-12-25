import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Long {
        val almanach = input.readAlmanach()

        var lowestLocation: Long = Long.MAX_VALUE
        almanach.seeds.forEach { seed ->
            var currentSource = seed
            almanach.mapsInOrder.forEach { mappings ->
                // Find destination for source
                // Set found destination as currentSource
                currentSource = mappings.findDestinationForSource(currentSource)
            }
            // Set currentSource as lowestLocation if it's lower than lowestLocation
            if (currentSource < lowestLocation) {
                lowestLocation = currentSource
            }
        }
        return lowestLocation
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 0)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

data class MappingEntry(
        val destinationRangeStart: Long,
        val sourceRangeStart: Long,
        val length: Long,
)

data class Almanach(
        val seeds: List<Long>,
        val mapsInOrder: List<List<MappingEntry>>,
)

private fun List<String>.readAlmanach(): Almanach {
    val seeds = readSeeds()
    val mapLines = drop(2)
    val maps = mutableMapOf<String, MutableList<MappingEntry>>()
    var currentMapHeader: String? = null
    mapLines.forEach {
        if (it.contains("map:")) {
            currentMapHeader = it
            maps[it] = mutableListOf()
        } else if (it.isEmpty()) {
            currentMapHeader = null
        } else if (currentMapHeader != null) {
            val (destinationStart, sourceStart, length) = it.splitToNumbers()
            maps.getValue(currentMapHeader!!).add(
                    MappingEntry(
                            destinationStart,
                            sourceStart,
                            length
                    )
            )
        }
    }
    return Almanach(
            seeds = seeds,
            mapsInOrder = listOf(
                    maps.getValue("seed-to-soil map:"),
                    maps.getValue("soil-to-fertilizer map:"),
                    maps.getValue("fertilizer-to-water map:"),
                    maps.getValue("water-to-light map:"),
                    maps.getValue("light-to-temperature map:"),
                    maps.getValue("temperature-to-humidity map:"),
                    maps.getValue("humidity-to-location map:"),
            ),
    )
}

private fun List<MappingEntry>.findDestinationForSource(currentSource: Long): Long {
    var destination: Long? = null
    forEach { mappingEntry ->
        val (destinationStart, sourceStart, length) = mappingEntry
        if (currentSource >= sourceStart && currentSource <= sourceStart + length) {
            destination = destinationStart + abs(currentSource-sourceStart)
        }
    }

    return destination ?: currentSource
}

private fun List<String>.readSeeds(): List<Long> {
    return this[0].split(": ")[1].splitToNumbers()
}

fun String.splitToNumbers() =
        split(" ").map { it.toLong() }
