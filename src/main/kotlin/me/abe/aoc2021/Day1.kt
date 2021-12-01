package me.abe.aoc2021

import me.abe.readLines
import kotlin.math.max
import kotlin.math.min

fun main() {
    val fileName = "day1.txt"

    val p1 = part1(fileName)
    println("Part 1 answer is : $p1")

    val p2 = part2(fileName)
    println("Part 2 answer is : $p2")
}


fun part1(inputfileName: String): Int {
    val inputVals = readLines(inputfileName).map { it.toInt() }

    var currentVal = inputVals[0]
    var largerMeasurements = 0
    inputVals.forEach {
        if (it > currentVal) {
            largerMeasurements++
        }
        currentVal = it
    }
    return largerMeasurements
}

fun part2(inputfileName: String): Int {
    val inputVals = readLines(inputfileName).map { it.toInt() }
    var largerMeasurements = 0
    var currentVal = aggregateNext3(inputVals, 0)
    inputVals.forEachIndexed { i, _ ->
        if ((aggregateNext3(inputVals, i) > currentVal)) {
            largerMeasurements++
        }
        currentVal = aggregateNext3(inputVals, i)
    }
    return largerMeasurements
}

fun aggregateNext3(values: List<Int>, index: Int): Int {
    val valuesCount = values.size
    return if (index < valuesCount - 2) (values[index] + values[index + 1] + values[index + 2]) else -1

}