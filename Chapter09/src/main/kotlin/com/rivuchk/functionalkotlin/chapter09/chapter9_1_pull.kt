package com.rivuchk.functionalkotlin.chapter09

fun main(args: Array<String>) {
    var list:List<Any> = listOf(1, "Two", 3, "Four", "Five", 5.5f) // 1
    var iterator = list.iterator() // 2
    while (iterator.hasNext()) { // 3
        println(iterator.next()) // Prints each element 4
    }
}
