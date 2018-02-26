package com.rivuchk.packtpub.functionalkotlin.chapter11

import java.util.stream.Stream

fun main(args: Array<String>) {
    val emptyStream = Stream.empty<String>()

    val item = emptyStream.findAny()
    println("Item is $item")
}