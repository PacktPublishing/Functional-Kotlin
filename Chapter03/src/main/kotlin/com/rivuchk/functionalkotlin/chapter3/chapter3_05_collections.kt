package com.rivuchk.functionalkotlin.chapter3

fun main(args: Array<String>) {
    val immutableList = listOf(1,2,3,4,5,6,7)//(1)
    println("Immutable List $immutableList")
    val mutableList:MutableList<Int> = immutableList.toMutableList()//(2)
    println("Mutable List $mutableList")
    mutableList.add(8)//(3)
    println("Mutable List after add $mutableList")
    println("Mutable List after add $immutableList")
}