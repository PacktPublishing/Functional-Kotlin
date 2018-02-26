package com.packtpub.functionalkotlin.chapter07

/*fun main(args: Array<String>) = runBlocking {
	val job = launch {
		repeat(1000) { i ->
			println("I'm sleeping = $i...")
			delay(500)
		}

	}
	delay(1300)
	println("main: I'm tired of waiting!")
	job.cancelAndJoin()
	println("main: Now I can quit")
}*/

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val startTime = System.currentTimeMillis()
	val job = launch {
		var nextPrimeTime = startTime
		var i = 0
		while (i < 5) {
			if(System.currentTimeMillis() >= nextPrimeTime) {
				println("I'm sleeping = ${i++}...")
				nextPrimeTime += 500L
			}
		}
	}
	delay(1300)
	println("main: I'm tired of waiting!")
	job.cancelAndJoin()
	println("main: Now I can quit.")
}*/

/*
fun main(args: Array<String>) = runBlocking {
	val startTime = System.currentTimeMillis()
	val job = launch {
		var nextPrimeTime = startTime
		var i = 0
		while (isActive) {
			if(System.currentTimeMillis() >= nextPrimeTime) {
				println("I'm sleeping = ${i++}...")
				nextPrimeTime += 500L
			}
		}
	}
	delay(1300)
	println("main: I'm tired of waiting!")
	job.cancelAndJoin()
	println("main: Now I can quit.")
}*/

/*fun main(args: Array<String>) = runBlocking {
	val job = launch {
		try {
			repeat(1000) { i ->
				println("I'm sleeping = $i...")
				delay(500)
			}
		} finally {
			println("I'm running finally")
		}

	}
	delay(1300)
	println("main: I'm tired of waiting!")
	job.cancelAndJoin()
	println("main: Now I can quit")
}*/


/*
fun main(args: Array<String>) = runBlocking<Unit> {
	withTimeout(1300L) {
		repeat(1000) { i ->
			println("I'm sleeping = $i...")
			delay(500)
		}
	}
}*/

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val result = withTimeoutOrNull(1300L) {
		repeat(1000) { i ->
			println("I'm sleeping = $i...")
			delay(500)
		}
		"Done"
	}
	println("Result is $result")
}*/
