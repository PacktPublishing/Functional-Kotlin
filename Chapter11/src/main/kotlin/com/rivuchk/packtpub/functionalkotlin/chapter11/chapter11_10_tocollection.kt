package com.rivuchk.packtpub.functionalkotlin.chapter11

import com.sun.deploy.util.OrderedHashSet
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.LinkedHashSet
import kotlin.streams.asStream

fun main(args: Array<String>) {
    val resultantSet = (0..10).asSequence().asStream()
            .collect(Collectors.toCollection{LinkedHashSet<Int>()})

    println("resultantSet $resultantSet")
}