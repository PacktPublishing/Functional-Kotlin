package com.rivuchk.functionalkotlin.chapter4

fun invokeSomeStuff(lambda:()->Unit) {
    lambda()
}

fun main(args: Array<String>) {
    invokeSomeStuff({
        println("doSomeStuff called");
    })
}
