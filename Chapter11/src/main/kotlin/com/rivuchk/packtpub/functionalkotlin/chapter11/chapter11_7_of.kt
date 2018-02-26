package com.rivuchk.packtpub.functionalkotlin.chapter11

import java.util.stream.Collectors
import java.util.stream.Stream

fun main(args: Array<String>) {
    val stream = Stream.of("Item 1",2,"Item 3",4,5.0,"Item 6")

    println("Items in Stream = ${stream.collect(Collectors.toList())}")
}