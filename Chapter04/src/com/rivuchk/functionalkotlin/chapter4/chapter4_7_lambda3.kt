package com.rivuchk.functionalkotlin.chapter4

fun main(args: Array<String>) {
    val reverse:(Int)->Int//(1)

    reverse = {
        var n = it
        var revNumber = 0
        while (n>0) {
            val digit = n%10
            revNumber=revNumber*10+digit
            n/=10
        }
        revNumber
    }// (2)
    println("reverse 123 ${reverse(123)}")
    println("reverse 456 ${reverse(456)}")
    println("reverse 789 ${reverse(789)}")
}