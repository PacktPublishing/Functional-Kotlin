package com.rivuchk.functionalkotlin.chapter4

import java.util.*

fun main(args: Array<String>) {
    val sum = { x: Int, y: Int -> x + y } // (1)
    println("Sum ${sum(10,13)}")// (2)
    println("Sum ${sum(52,68)}")// (3)
}