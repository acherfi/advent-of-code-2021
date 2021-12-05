package me.abe.aoc2021

import me.abe.createGroupedLines
import me.abe.readLines
import me.abe.toward
import kotlin.math.absoluteValue

fun main() {
    val grid = FloorGrid(1000, 1000)
    val lines = readLines("day5.txt").map { Line(it) }

    println("Part 1 answer is : ${part1(grid, lines)}")
    println("Part 2 answer is : ${part2(grid, lines)}")
}

private fun part1(grid: FloorGrid, lines: List<Line>): Int {
    lines.filter { it.isHorizontal() || it.isVertical() }.forEach { grid.applyLine(it) }
    return grid.score()
}

private fun part2(grid: FloorGrid, lines: List<Line>): Int {
    lines.filter { it.isDiagonal() }.forEach { grid.applyLine(it) }
    return grid.score()
}


class Line(str: String) {
    val x1: Int
    val y1: Int
    val x2: Int
    val y2: Int

    init {
        val coords = fromString(str)
        x1 = coords[0]; y1 = coords[1]; x2 = coords[2]; y2 = coords[3]
    }

    private fun fromString(str: String) = str.split(" -> ").map { it.split(",") }
        .reduce { acc, list -> acc + list }.map(String::toInt)

    fun isHorizontal() = (x1 == x2)
    fun isVertical() = (y1 == y2)
    fun isDiagonal() = ((x1 - x2).absoluteValue == (y1 - y2).absoluteValue)
}

class FloorGrid(height: Int = 10, width: Int = 10) {
    private val grid = List(height) { MutableList(width) { 0 } }

    fun applyLine(line: Line) {
        if (line.isHorizontal() || line.isVertical()) {
            for (i in line.x1 toward line.x2)
                for (j in line.y1 toward line.y2) grid[i][j] += 1
        }
        else if (line.isDiagonal()) {
            val x = (line.x1 toward line.x2).toList()
            val y = (line.y1 toward line.y2).toList()
                y.forEachIndexed { i, _ -> grid[x[i]][y[i]] += 1 }
        }
    }

    fun score(minValue: Int = 2) = grid.sumOf { it.count { it >= minValue } }
}