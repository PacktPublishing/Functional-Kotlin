package com.rivuchk.packtpub.functionalkotlin.chapter06

fun useDelegate(shouldPrint:Boolean) {
    val localDelegate by lazy {
        "Delegate Used"
    }
    if(shouldPrint) {
        println(localDelegate)
    }

    println("bye bye")
}

fun main(args: Array<String>) {
    useDelegate(true)
    useDelegate(false)
}