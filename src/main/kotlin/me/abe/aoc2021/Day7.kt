package me.abe.aoc2021

import me.abe.readLines
import kotlin.math.absoluteValue

fun main() {
    val fileName = "day7.txt"
    val crabPositions = readLines(fileName)[0].split(",").map(String::toInt)

    val optimalPosition1 = getOptimalPosition(crabPositions)
    println("Part 1 answer is : ${computeFuelConsumption(crabPositions, optimalPosition1)}")

    val optimalPosition2 = getOptimalPosition(crabPositions) { x1, x2 -> getTrueMoveCost(x1, x2) }
    println(
        "Part 2 answer is : ${
            computeFuelConsumption(
                crabPositions,
                optimalPosition2
            ) { x1, x2 -> getTrueMoveCost(x1, x2) }
        }"
    )
}

fun getOptimalPosition(
    crabPositions: List<Int>,
    moveCost: (Int, Int) -> Int = { x1, x2 -> (x1 - x2).absoluteValue }
): Int {
    return (0..(crabPositions.maxOrNull() ?: 0))
        .minByOrNull { computeFuelConsumption(crabPositions, it, moveCost) } ?: 0
}

fun computeFuelConsumption(
    crabPositions: List<Int>, position: Int,
    moveCost: (Int, Int) -> Int = { x1, x2 -> (x1 - x2).absoluteValue }
): Int {
    return crabPositions.sumOf { moveCost(it, position) }
}


fun getTrueMoveCost(position1: Int, position2: Int) = (0..(position1 - position2).absoluteValue).sum()
