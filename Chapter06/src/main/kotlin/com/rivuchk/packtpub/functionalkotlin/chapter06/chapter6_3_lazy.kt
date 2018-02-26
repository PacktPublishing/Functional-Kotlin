package com.rivuchk.packtpub.functionalkotlin.chapter06

val myLazyVal:String by lazy {
    println("Just Initialised")
    "My Lazy Val"
}

fun main(args: Array<String>) {
    println("Not yet initialised")
    println(myLazyVal)
}