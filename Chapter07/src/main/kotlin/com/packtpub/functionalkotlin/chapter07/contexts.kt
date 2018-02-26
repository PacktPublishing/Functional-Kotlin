package com.packtpub.functionalkotlin.chapter07

import kotlinx.coroutines.experimental.*

fun main(args: Array<String>) = runBlocking {
	println("run blocking coroutineContext = $coroutineContext")
	println("coroutineContext[Job] = ${coroutineContext[Job]}")
	println(Thread.currentThread().name)
	println("-----")

	val jobs = listOf(
			launch {
				println("launch coroutineContext = $coroutineContext")
				println("coroutineContext[Job] = ${coroutineContext[Job]}")
				println(Thread.currentThread().name)
				println("-----")
			},
			async {
				println("async coroutineContext = $coroutineContext")
				println("coroutineContext[Job] = ${coroutineContext[Job]}")
				println(Thread.currentThread().name)
				println("-----")
			},
			launch(CommonPool) {
				println("common launch coroutineContext = $coroutineContext")
				println("coroutineContext[Job] = ${coroutineContext[Job]}")
				println(Thread.currentThread().name)
				println("-----")
			},
			launch(coroutineContext) {
				println("inherit launch coroutineContext = $coroutineContext")
				println("coroutineContext[Job] = ${coroutineContext[Job]}")
				println(Thread.currentThread().name)
				println("-----")
			}
	)

	jobs.forEach { job ->
		println("job = $job")
		job.join()
	}
}


/*fun main(args: Array<String>) = runBlocking {

	val job = launch {
		repeat(1_000_000) {
			launch(coroutineContext) {
				delay(1000)
				print('.')
			}
		}
	}

	job.join()
}*/
