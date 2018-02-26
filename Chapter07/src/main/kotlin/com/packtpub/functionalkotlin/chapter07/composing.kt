package com.packtpub.functionalkotlin.chapter07

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay

suspend fun doSomethingUsefulOne(): Int {
	delay(1000L)
	return 13
}

suspend fun doSomethingUsefulTwo(): Int {
	delay(1000L)
	return 29
}

fun asyncSomethingUsefulOne() = async {
	doSomethingUsefulOne()
}

fun asyncSomethingUsefulTwo() = async {
	doSomethingUsefulTwo()
}

/*fun main(args: Array<String>) = runBlocking<Unit> {
	val time = measureTimeMillis {
		val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
		val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
		println("The answer is ${one.await() + two.await()}")
	}
	println("Completed in  = $time ms")
}*/

/*
fun main(args: Array<String>) {
	val time = measureTimeMillis {
		val one = asyncSomethingUsefulOne()
		val two = asyncSomethingUsefulTwo()
		runBlocking {
			println("The answer is ${one.await() + two.await()}")
		}
	}
	println("Completed in  = $time ms")
}*/
