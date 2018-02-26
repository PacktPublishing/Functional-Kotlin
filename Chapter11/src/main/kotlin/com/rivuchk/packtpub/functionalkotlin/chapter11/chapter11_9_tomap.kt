package com.rivuchk.packtpub.functionalkotlin.chapter11

import java.util.stream.Collectors
import kotlin.streams.asStream

fun main(args: Array<String>) {
    val resultantMap = (0..10).asSequence().asStream()
            .collect(Collectors.toMap<Int,Int,Int>({
                it
            },{
                it*it
            }))
    println("resultantMap = $resultantMap")
}