package me.abe.aoc2021

import me.abe.readLines

fun main() {
    var countOf1478 = 0
    var sumOfCodes = 0

    val lines = readLines("day8.txt")
    lines.forEach {
        val line = it.split(" | ")
        val digitList = line[0].split(" ")
        val codeList = line[1].split(" ")
        val dr = DigitRecognitor(digitList)

        // part1 answer computing
        for (i in listOf(1, 4, 7, 8)) countOf1478 += dr.count(codeList, i)

        // part2 answer computing
        sumOfCodes += dr.decode(codeList)
    }
    println("Part 1 answer is : $countOf1478")
    println("Part 2 answer is : $sumOfCodes")
}


class DigitRecognitor(private val list: List<String>) {
    private val digitMap = HashMap<Int, Set<Char>>()

    fun find(digit: Int): Set<Char> {
        when {
            digitMap[digit] != null -> return digitMap[digit]!!
            digit == 1 -> {
                val set = list.find { it.length == 2 }!!.toSet()
                digitMap[digit] = set
                return set
            }
            digit == 7 -> {
                val set = list.find { it.length == 3 }!!.toSet()
                digitMap[digit] = set
                return set
            }
            digit == 4 -> {
                val set = list.find { it.length == 4 }!!.toSet()
                digitMap[digit] = set
                return set
            }
            digit == 8 -> {
                val set = list.find { it.length == 7 }!!.toSet()
                digitMap[digit] = set
                return set
            }
            digit == 9 -> {
                val set4 = find(4)
                val topChar: Char = findTopBar()
                val set =
                    list.find { it.length == 6 && it.toSet().containsAll(set4) && it.contains(topChar) }!!.toSet()
                digitMap[digit] = set
                return set
            }
            digit == 5 -> {
                val set9 = find(9)
                val set1 = find(1)
                val set =
                    list.find { it.length == 5 && set9.containsAll(it.toSet()) && !it.toSet().containsAll(set1) }!!
                        .toSet()
                digitMap[digit] = set
                return set
            }
            digit == 3 -> {
                val set9 = find(9)
                val set1 = find(1)
                val set =
                    list.find { it.length == 5 && set9.containsAll(it.toSet()) && it.toSet().containsAll(set1) }!!
                        .toSet()
                digitMap[digit] = set
                return set
            }
            digit == 6 -> {
                val set1 = find(1)
                val set =
                    list.find { it.length == 6 && !it.toSet().containsAll(set1) }!!
                        .toSet()
                digitMap[digit] = set
                return set
            }
            digit == 0 -> {
                val set9 = find(9)
                val set6 = find(6)
                val set =
                    list.find { it.length == 6 && !set9.containsAll(it.toSet()) && !set6.containsAll(it.toSet()) }!!
                        .toSet()
                digitMap[digit] = set
                return set
            }
            digit == 2 -> {
                val set5 = find(5)
                val set3 = find(3)
                val set =
                    list.find { it.length == 5 && !set5.containsAll(it.toSet()) && !set3.containsAll(it.toSet()) }!!
                        .toSet()
                digitMap[digit] = set
                return set
            }
            else -> return setOf()
        }
    }

    fun count(codeList: List<String>, digit: Int): Int {
        return codeList.count { it.toSet() == find(digit) }
    }

    private fun findTopBar(): Char {
        return find(7).find { !find(1).contains(it) }!!
    }

    fun decode(code: List<String>): Int {
        for (i in 0..9) find(i)
        return code.map { it.toDigit().toString() }.reduce { acc, s -> acc + s }.toInt()
    }

    private fun String.toDigit():Int {
        digitMap.forEach { (t, u) -> if (this.toSet() == u) return t }
        return -1
    }
}

