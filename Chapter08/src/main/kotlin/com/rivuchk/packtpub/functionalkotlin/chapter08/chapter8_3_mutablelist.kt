package com.rivuchk.packtpub.functionalkotlin.chapter08

fun main(args: Array<String>) {
    val list = mutableListOf(1,2,4)

    for (i in list) {
        println("for1 item $i")
    }

    println("-----Adding Items-----")

    list.add(5)
    list.add(2,3)
    list.add(6)

    for (i in list) {
        println("for2 item $i")
    }
}