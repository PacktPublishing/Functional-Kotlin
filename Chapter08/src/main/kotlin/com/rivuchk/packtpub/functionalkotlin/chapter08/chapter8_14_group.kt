package com.rivuchk.packtpub.functionalkotlin.chapter08

fun main(args: Array<String>) {
    val list = 1.rangeTo(50).toList()

    println(list.groupBy { it%5 })
}