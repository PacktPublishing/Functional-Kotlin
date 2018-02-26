package com.rivuchk.packtpub.functionalkotlin.chapter11

import java.util.stream.Collectors
import java.util.stream.Stream

fun main(args: Array<String>) {
    val stream = Stream.builder<String>()
            .add("Item 1")
            .add("Item 2")
            .add("Item 3")
            .add("Item 4")
            .add("Item 5")
            .add("Item 6")
            .add("Item 7")
            .add("Item 8")
            .add("Item 9")
            .add("Item 10")
            .build()

    println("The Stream is ${stream.collect(Collectors.toList())}")
}