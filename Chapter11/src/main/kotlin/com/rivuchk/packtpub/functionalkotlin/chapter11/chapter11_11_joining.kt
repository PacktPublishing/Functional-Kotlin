package com.rivuchk.packtpub.functionalkotlin.chapter11

import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.streams.asStream

fun main(args: Array<String>) {
    val resultantString = Stream.builder<String>()
            .add("Item 1")
            .add("Item 2")
            .add("Item 3")
            .add("Item 4")
            .add("Item 5")
            .add("Item 6")
            .build()
            .collect(Collectors.joining(" - ","Starts Here=>","<=Ends Here"))

    println("resultantString $resultantString")
}