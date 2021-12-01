package me.abe

import java.io.File
import java.io.InputStream

fun readLines(inputFileName: String): List<String> {
    val inputStream: InputStream = File("src/main/resources/inputs/$inputFileName").inputStream()
    val lineList = ArrayList<String>()

    inputStream
        .bufferedReader()
        .useLines { lines -> lines.forEach { lineList.add(it) } }

    return lineList
}
