package com.rivuchk.functionalkotlin.chapter3


import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking

class MyData {
    var someData:Int = 0
}

fun main(args: Array<String>) {
    val myData:MyData = MyData()

    async(CommonPool) {
        for(i in 11..20) {
            myData.someData+=i
            println("someData from 1st async ${myData.someData}")
            delay(500)
        }
    }

    async(CommonPool) {
        for(i in 1..10) {
            myData.someData++
            println("someData from 2nd async ${myData.someData}")
            delay(300)
        }
    }

    runBlocking { delay(10000) }
}