package com.rivuchk.functionalkotlin.chapter4

fun getUser():Pair<Int,String> {
    return Pair(1,"Rivu")
}

fun main(args: Array<String>) {
    val (userID,userName) = getUser()

    println("User ID: $userID \t User Name: $userName")
}