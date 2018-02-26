package com.rivuchk.functionalkotlin.chapter4

import java.util.regex.Pattern

fun String.countWords():Int {
    return trim()
            .split(Pattern.compile("\\s+"))
            .size
}

fun main(args: Array<String>) {
    val counts = "This is an example String\nWith multiple words".countWords()
    println("Count Words: $counts")
}