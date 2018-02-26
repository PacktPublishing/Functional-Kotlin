package com.rivuchk.functionalkotlin.chapter4


fun performOperationOnEven(number:Int,operation:(Int)->Int):Int {
    if(number%2==0) {
        return operation(number)
    } else {
        return number
    }
}

fun main(args: Array<String>) {
    println("Called with 4,(it*2): ${performOperationOnEven(4,
            {it*2})}")
    println("Called with 5,(it*2): ${performOperationOnEven(5,
            {it*2})}")
}