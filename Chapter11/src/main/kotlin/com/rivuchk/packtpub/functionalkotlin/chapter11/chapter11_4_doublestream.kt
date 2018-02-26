package com.rivuchk.packtpub.functionalkotlin.chapter11

import java.util.stream.DoubleStream

fun main(args: Array<String>) {
    val doubleStream = DoubleStream.iterate(1.5,{item->item*1.3})//(1)

    val avg = doubleStream
            .limit(10)//(2)
            .peek {
                println("Item $it")
            }.average()//(3)

    println("Average of 10 Items $avg")
}