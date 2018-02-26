package com.rivuchk.functionalkotlin.chapter4

class Calc {
    var a:Int=0
    var b:Int=0

    fun addNumbers(a:Int = this.a,b:Int = this.b):Int {
        this.a = a
        this.b = b
        return a+b
    }
}

fun main(args: Array<String>) {
    val calc = Calc()
    println("Result is ${calc.addNumbers(10,15)}")
}