package com.rivuchk.packtpub.functionalkotlin.chapter11

import java.util.stream.Collectors
import kotlin.streams.asStream

fun main(args: Array<String>) {
    val stream = 1.rangeTo(10).asSequence().asStream()

    val resultantList = stream.parallel().filter{
        it%2==0
    }.collect(Collectors.toList())

    println(resultantList)
}