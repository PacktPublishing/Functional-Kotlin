package com.packtpub.functionalkotlin.chapter07

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

/*
fun main(args: Array<String>) {
	launch {
		delay(1000L)
		println("World!")
	}
	println("Hello, ")
	Thread.sleep(2000L)
}
*/

/*fun main(args: Array<String>) {
	launch{
		delay(1000)
		println("World!")
	}
	print("Hello, ")
	runBlocking {
		delay(2000)
	}
}*/

/*fun main(args: Array<String>) = runBlocking {
	launch {
		delay(1000)
		println("World!")
	}
	print("Hello, ")

	delay(2000)
}*/

/*
fun main(args: Array<String>) = runBlocking {
	val job = launch {
		doWorld()
	}
	print("Hello, ")
	job.join()
}

private suspend fun doWorld() {
	delay(1000)
	println("World!")
}*/

fun main(args: Array<String>) = runBlocking {
	val jobs = List(100_000) {
		launch {
			delay(1000)
			print(".")
		}
	}

	jobs.forEach { it.join() }
}

/*
fun main(args: Array<String>) = runBlocking{
	launch {
		repeat(1000){ i ->
			println("I'm sleeping $i...")
			delay(500)
		}
	}
	delay(1300)
}*/

/*fun main(args: Array<String>) {
	thread {
		Thread.sleep(1000)
		println("World!")
	}
	print("Hello ")
	Thread.sleep(2000)
}*/

/*fun main(args: Array<String>) {
	val computation = thread {
		Thread.sleep(1000)
		println("World!")
	}
	print("Hello ")
	computation.join()
}*/

/*fun main(args: Array<String>) = awaitEnter{
	pauseUntilEnter()
	val threads = List(1900){
		thread {
			Thread.sleep(1000)
			print('.')
		}
	}
	threads.forEach(Thread::join)
}*/

/*fun main(args: Array<String>) = awaitEnter{
	pauseUntilEnter()
	val pool = Executors.newFixedThreadPool(1024)
	repeat(10000){
		pool.submit {
			Thread.sleep(1000)
			print('.')
		}
	}
	pool.shutdown()
	pool.awaitTermination(2, TimeUnit.SECONDS)
}*/

/*fun main(args: Array<String>) = runBlocking {
	launch {
		delay(1000)
		println("World")
	}
	print("Hello ")
	delay(2000)
}*/

/*fun main(args: Array<String>) = runBlocking {
	val job = launch {
		delay(1000)
		println("World")
	}
	print("Hello ")
	job.join()
}*/

/*fun main(args: Array<String>) = awaitEnter {
	pauseUntilEnter()
	runBlocking {
		val jobs = List(1_000_000) {
			launch {
				delay(1000)
				print('.')
			}
		}
		jobs.forEach { job -> job.join() }
	}
}*/




