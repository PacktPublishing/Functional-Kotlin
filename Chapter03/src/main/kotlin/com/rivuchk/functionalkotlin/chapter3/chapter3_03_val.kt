package com.rivuchk.functionalkotlin.chapter3

object MutableVal {
    var count = 0
    val myString:String = "Mutable"
        get() {//(1)
            return "$field ${++count}"//(2)
        }
}

fun main(args: Array<String>) {
    println("Calling 1st time ${MutableVal.myString}")
    println("Calling 2nd time ${MutableVal.myString}")
    println("Calling 3rd time ${MutableVal.myString}")//(3)
}