package com.rivuchk.packtpub.functionalkotlin.chapter11

import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

fun ClosedRange<Int>.random() =
        Random().nextInt(endInclusive - start) +  start

fun main(args: Array<String>) {
    val stream = Stream.generate {
        //return a random number
        (1..20).random()
    }

    val resultantList = stream
            .limit(10)
            .collect(Collectors.toList())

    println("resultantList = $resultantList")
}