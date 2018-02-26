package com.rivuchk.packtpub.functionalkotlin.chapter11

import java.util.stream.IntStream

fun main(args: Array<String>) {
    val intStream = IntStream.range(1,10)
    val result = intStream.sum()
    println("The sum of elements is $result")
}