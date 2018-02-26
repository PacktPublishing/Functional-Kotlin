package com.rivuchk.functionalkotlin.chapter4

fun main(args: Array<String>) {
    fun nested():String {
        return "String from nested function"
    }
    println("Nested Output: ${nested()}")
}