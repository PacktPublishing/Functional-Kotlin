package com.rivuchk.functionalkotlin.chapter4

fun getAnotherFunction(n:Int):(String)->Unit {
    return {
        println("n:$n it:$it")
    }
}

fun main(args: Array<String>) {
    getAnotherFunction(0)("abc")
    getAnotherFunction(2)("def")
    getAnotherFunction(3)("ghi")
}