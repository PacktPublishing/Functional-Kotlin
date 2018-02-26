package com.packtpub.functionalkotlin.chapter02


/*
fun main(args: Array<String>) {
    val i by lazy {
        println("Lazy evaluation")
        1
    }

    println("before using i")
    println(i)
}*/


/*
fun main(args: Array<String>) {
    val size = listOf(2 + 1, 3 * 2, 1 / 0, 5 - 4).size
}*/

fun main(args: Array<String>) {
    val size = listOf({ 2 + 1 }, { 3 * 2 }, { 1 / 0 }, { 5 - 4 }).size
}