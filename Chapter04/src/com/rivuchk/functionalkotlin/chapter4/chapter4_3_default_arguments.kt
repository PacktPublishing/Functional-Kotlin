package com.rivuchk.functionalkotlin.chapter4

fun Int.isGreaterThan(anotherNumber:Int=0):Boolean {
    return this>anotherNumber
}


fun main(args: Array<String>) {
    println("5>0: ${5.isGreaterThan()}")
    println("5>6: ${5.isGreaterThan(6)}")
}