package me.abe.aoc2021

import me.abe.readLines


fun main() {
    val fileName = "day6.txt"
    val simulator = FishSimulator(readLines(fileName)[0]
        .split(",").map(String::toInt).toMutableList())

    simulator.next(80)
    println("Part 1 answer is : ${simulator.count()}")

    simulator.next(256 - 80)
    println("Part 2 answer is : ${simulator.count()}")
}

private class FishSimulator(fishes: List<Int>) {
    private val PERIOD = 6
    private val FIRST_PERIOD = PERIOD + 2
    private var day = 0

    private var fishMap = mutableMapOf<Int, Long>()

    init {
        for (i in 0..FIRST_PERIOD) fishMap[i] = fishes.count { it == i }.toLong()
    }

    fun next() {
        val newFishes = fishMap.getOrDefault(0, 0)
        val tempMap = mutableMapOf<Int, Long>()
        fishMap.forEach { if (it.key > 0) tempMap[it.key - 1] = it.value }
        fishMap = tempMap
        fishMap[PERIOD] = fishMap.getOrDefault(PERIOD, 0) + newFishes
        fishMap[FIRST_PERIOD] = newFishes
    }

    fun next(steps: Int) {
        repeat(steps) {
            next()
            day++
        }
    }
    fun count() = fishMap.values.sum()
}