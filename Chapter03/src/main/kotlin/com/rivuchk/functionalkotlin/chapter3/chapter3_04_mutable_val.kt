package com.rivuchk.functionalkotlin.chapter3

class MutableObj {
    var value = ""

    override fun toString(): String {
        return "MutableObj(value='$value')"
    }
}

fun main(args: Array<String>) {
    val mutableObj:MutableObj = MutableObj()//(1)
    println("MutableObj $mutableObj")
    mutableObj.value = "Changed"//(2)
    println("MutableObj $mutableObj")

    val list = mutableListOf("a","b","c","d","e")//(3)
    println(list)
    list.add("f")//(4)
    println(list)
}