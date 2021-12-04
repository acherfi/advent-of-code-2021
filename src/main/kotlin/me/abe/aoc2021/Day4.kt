package me.abe.aoc2021

import me.abe.createGroupedLines
import me.abe.readLines


fun main() {
    val fileName = "day4.txt"

    val p1 = part1(fileName)
    println("Part 1 answer is : $p1")

    val p2 = part2(fileName)
    println("Part 2 answer is : $p2")
}

private fun part1(inputFileName: String): Int {
    val groupedLines = createGroupedLines(readLines(inputFileName), "")
    val pool = BingoPool(groupedLines[0][0])
    val grids = groupedLines.subList(1, groupedLines.size).map { BingoGrid(it) }

    val game = BingoGame(pool, grids)
    val winner = game.runUntilFirstWinner()

    return computeAnswer(winner, game.getLastDrawnItem())
}

private fun part2(inputFileName: String): Int {
    val groupedLines = createGroupedLines(readLines(inputFileName), "")
    val pool = BingoPool(groupedLines[0][0])
    val grids = groupedLines.subList(1, groupedLines.size).map { BingoGrid(it) }

    val game = BingoGame(pool, grids)
    val winner = game.runUntilLastWinner()

    return computeAnswer(winner, game.getLastDrawnItem())
}

private fun computeAnswer(winnerGrid: BingoGrid, lastDrawnItem: String): Int {
    return winnerGrid.getUnmarkedItems().map(String::toInt).sum() * lastDrawnItem.toInt()
}


class BingoPool(sequenceString: String) {

    private val sequence = fromString(sequenceString)
    private var index: Int = -1

    private fun fromString(sequenceString: String) = sequenceString.split(",")

    fun draw(): String {
        index++
        return sequence[index]
    }

    fun getLastDrawn() = sequence[index]

}

class BingoGrid(gridLines: List<String>) {

    private val grid = fromStringList(gridLines)
    private val drawnGrid = initDrawnGrid()

    private fun fromStringList(gridLines: List<String>): List<List<String>> {
        val grid = arrayListOf<List<String>>()
        gridLines.forEach { grid.add(it.trim().split(" +".toRegex())) }
        return grid
    }

    private fun initDrawnGrid(): ArrayList<BooleanArray> {
        val drawnGrid = arrayListOf<BooleanArray>()
        for (i in grid.indices)
            drawnGrid.add(BooleanArray(grid[0].size) { false })
        return drawnGrid
    }

    // mark and return true if the the item exist
    fun markIfExist(item: String): Boolean {
        grid.forEachIndexed { i, line ->
            line.forEachIndexed { j, it ->
                if (it == item) {
                    drawnGrid[i][j] = true
                    return true
                }
            }
        }
        return false
    }


    private fun getCompletedLineIndex(): Int {
        drawnGrid.forEachIndexed { i, line ->
            if (line.reduce { acc: Boolean, b: Boolean -> (acc && b) }) return i
        }
        return (-1)
    }

    private fun hasCompletedLine(): Boolean = (getCompletedLineIndex() >= 0)

    private fun getCompletedColumnIndex(): Int {

        drawnGrid[0].forEachIndexed { j, _ ->
            var acc = true
            drawnGrid.forEachIndexed { i, line -> acc = (acc && line[j]) }
            if (acc) return j
        }
        return (-1)
    }

    private fun hasCompletedColumn(): Boolean = (getCompletedColumnIndex() >= 0)

    fun isCompleted(): Boolean = hasCompletedLine() || hasCompletedColumn()

    fun getUnmarkedItems(): List<String> {
        val unmarked = ArrayList<String>()
        drawnGrid.forEachIndexed { i, line -> line.forEachIndexed() { j, it -> if (!it) unmarked.add(grid[i][j]) } }
        return unmarked
    }
}

class BingoGame(private val pool: BingoPool, private val grids: List<BingoGrid>) {

    fun runUntilFirstWinner(): BingoGrid {
        while (true) {
            val item = pool.draw()
            grids.forEach {
                it.markIfExist(item)
                if (it.isCompleted())
                    return it
            }
        }
    }

    fun runUntilLastWinner(): BingoGrid {
        var grid: BingoGrid = grids[0]
        while (true) {
            val item = pool.draw()
            val uncompleteGrids = grids.filter { !it.isCompleted() }
            uncompleteGrids.forEach {
                    it.markIfExist(item)
                    grid = it
            }
            if (uncompleteGrids.none { !it.isCompleted() }) return grid
        }
    }

    fun getLastDrawnItem() = pool.getLastDrawn()
}