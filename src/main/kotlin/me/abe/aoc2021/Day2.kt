package me.abe.aoc2021

import me.abe.readLines


fun main() {
    val fileName = "day2.txt"

    val p1 = part1(fileName)
    println("Part 1 answer is : ${p1.first * p1.second}")

    val p2 = part2(fileName)
    println("Part 2 answer is : ${p2.first * p2.second}")
}

class Submarine {
    private var h = 0
    private var v = 0
    private var aim = 0


    fun moveP1(direction: String, amplitude: Int) {
        when (direction) {
            "forward" -> h += amplitude
            "down" -> v += amplitude
            "up" -> v -= amplitude
            else -> println("WARNING: direction '$direction' does not exist.")
        }
    }

    fun moveP2(direction: String, amplitude: Int) {
        when (direction) {
            "forward" -> {
                h += amplitude
                v += amplitude * aim
            }
            "down" -> aim += amplitude
            "up" -> aim -= amplitude
            else -> println("WARNING: direction '$direction' does not exist.")
        }
    }

    fun getPosition(): Pair<Int, Int> {
        return Pair(h, v)
    }
}

private fun processMovement(inputfileName: String, move: (String, Int) -> Unit) {
    readLines(inputfileName).map {
        val splitted = it.split(" ")
        move(splitted[0], splitted[1].toInt())
    }
}

private fun part1(inputfileName: String): Pair<Int, Int> {
    val submarine = Submarine()
    processMovement(inputfileName, submarine::moveP1)
    return submarine.getPosition()
}

private fun part2(inputfileName: String): Pair<Int, Int> {
    val submarine = Submarine()
    processMovement(inputfileName, submarine::moveP2)
    return submarine.getPosition()
}

