package com.rivuchk.packtpub.functionalkotlin.chapter11

import java.util.stream.Collectors
import kotlin.streams.asStream

fun main(args: Array<String>) {
    val resultantSet = (1..20).asSequence().asStream()
            .collect(Collectors.groupingBy<Int,Int> { it%5 })

    println("resultantSet $resultantSet")
}