package me.abe

import java.io.File

fun readLines(inputFileName: String) = File("src/main/resources/inputs/$inputFileName").readLines()


fun createGroupedLines(lineList: List<String>, separator: String): List<List<String>> {
    val entries = ArrayList<List<String>>()
    var groupedLines = ArrayList<String>()
    lineList.forEach { if(it != separator) groupedLines.add(it) else {
        entries.add(groupedLines)
        groupedLines = ArrayList<String>()
    } }
    if (groupedLines.size != 0) entries.add(groupedLines)
    return entries
}

infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}