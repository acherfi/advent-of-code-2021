package me.abe.aoc2021

import me.abe.readLines

fun main() {
    val fileName = "day3.txt"

    val p1 = part1(fileName)
    println("Part 1 answer is : $p1")

    val p2 = part2(fileName)
    println("Part 2 answer is : $p2")
}

private fun part1(inputFileName: String): Int {
    val inputLines = readLines(inputFileName)
    val factors = computeFactors(inputLines)

    return factors.first.toInt(radix = 2) * factors.second.toInt(radix = 2)
}

private fun part2(inputFileName: String): Int {
    val inputLines = readLines(inputFileName)
    val factors = computeFactors(inputLines)

    val ratingO2 = findRating(inputLines, factors, mode = "GAMMA")
    val ratingCO2 = findRating(inputLines, factors, mode = "EPSILON")
    return ratingO2 * ratingCO2
}


private fun computeGamma(oneCounter: Int, numberOfLines: Int) =
    if (oneCounter >= numberOfLines - oneCounter) 1 else 0

private fun computeFactors(inputLines: List<String>): Pair<String, String> {
    val lineSize = inputLines[0].length
    var gammaFactor = ""
    var epsilonFactor = ""

    for (i in 0 until lineSize) {
        val gamma = computeGamma(
            inputLines.sumOf { (if (it[i] == '1') 1 else 0) as Int },
            inputLines.size
        )
        gammaFactor += gamma.toString()
        epsilonFactor += (1 - gamma).toString()
    }
    return Pair(gammaFactor, epsilonFactor)
}

private fun findRating(lines: List<String>, factors: Pair<String, String>, position: Int = 0, mode: String):Int {
    if (lines.size == 1) return lines[0].toInt(radix = 2)
    else{
        val filtredLines = lines.filter {
            val factor = getFactor(factors, mode)
            it[position] == factor[position]
        }
        return findRating(filtredLines, computeFactors(filtredLines), position+1, mode)
    }
}

private fun getFactor(factors: Pair<String, String>, mode: String):String =
    when (mode){
        "GAMMA" -> factors.first
        "EPSILON" -> factors.second
        else -> {
            println("WARNING: mode '$mode' is unknown.")
            "ERROR"
        }
    }