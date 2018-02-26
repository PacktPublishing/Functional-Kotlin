package com.packtpub.functionalkotlin.chapter02


fun f(x: Long) : Long {
   return x * x // no access to external state
}

/*
fun main(args: Array<String>) {
    println(f(5))
    println(f(5))
    println(f(5))
}*/


fun main(args: Array<String>) {
    var i = 0

    fun g(x: Long): Long {
       return x * i // accessing mutable state
    }

    println(g(1)) //0
    i++
    println(g(1)) //1
    i++
    println(g(1)) //2
}