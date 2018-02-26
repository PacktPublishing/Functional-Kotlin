package com.rivuchk.functionalkotlin.chapter3

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking

class MyDataImmutable {
    val someData:Int = 0
}

fun main(args: Array<String>) {
    val myData: MyDataImmutable = MyDataImmutable()

    async(CommonPool) {
        var someDataCopy = myData.someData
        for (i in 11..20) {
            someDataCopy += i
            println("someData from 1st async $someDataCopy")
            delay(500)
        }
    }

    async(CommonPool) {
        var someDataCopy = myData.someData
        for (i in 1..10) {
            someDataCopy++
            println("someData from 2nd async $someDataCopy")
            delay(300)
        }
    }

    runBlocking { delay(10000) }
}