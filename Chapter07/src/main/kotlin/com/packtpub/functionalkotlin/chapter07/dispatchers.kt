package com.packtpub.functionalkotlin.chapter07

fun workingOn() = "I'm working in thread ${Thread.currentThread().name}"

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")
fun log(msg: Any) = log(msg.toString())


/*fun main(args: Array<String>) = runBlocking<Unit> {
	val jobs = listOf(
			launch(Unconfined) {
				println("Unconfined: ${workingOn()}")
			},
			launch(coroutineContext) {
				println("coroutineContext: ${workingOn()}")
			},
			launch(CommonPool) {
				println("CommonPool: ${workingOn()}")
			},
			launch(newSingleThreadContext("MyOwnThread")) {
				println("newSTC: ${workingOn()}")
			}
	)
	jobs.forEach { it.join() }
}*/

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val jobs = listOf(
			launch(Unconfined) {
				println("Unconfined: ${workingOn()}")
				delay(500)
				println("Unconfined: ${workingOn()}")
			},
			launch(coroutineContext) {
				println("coroutineContext: ${workingOn()}")
				delay(500)
				println("coroutineContext: ${workingOn()}")
			}
	)
	jobs.forEach { it.join() }
}*/

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val a = async(coroutineContext) {
		log("I'm computing a piece of the answer")
		6
	}

	val b = async(coroutineContext) {
		log("I'm computing another piece of the answer")
		7
	}

	log("The answer is ${a.await()  + b.await()}")
}*/

/*
fun main(args: Array<String>) {
	newSingleThreadContext("Ctx1").use { ctx1 ->
		newSingleThreadContext("Ctx2").use { ctx2 ->
			runBlocking(ctx1) {
				println("Job is ${coroutineContext[Job]}")
				log("Started in ctx1")
				kotlinx.coroutines.experimental.run(ctx2) {
					log("Working in ctx2")
				}
				log("Back to ctx1")
			}

		}
	}
}*/

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val request = launch {
		val job1 = launch {
			log("Job1: I have my own context and execute independently")
			delay(1000)
			log("job1: I am not affected by cancellation of the request")
		}

		val job2 = launch(coroutineContext) {
			delay(100)
			log("job2: I am child of the request coroutine")
			delay(1000)
			log("job2: I will not execute this line of my parent request is cancelled")
		}

		job1.join()
		job2.join()
	}
	delay(500)
	request.cancel()
	delay(1000)
	log("main: Who has survived request cancellation?")
}*/

/*fun main(args: Array<String>) = runBlocking<Unit> {
	val request = launch(coroutineContext) {
		val job1 = launch(coroutineContext + CommonPool) {
			log("Job1: I have my own context and execute independently")
			delay(1000)
			log("job1: I am not affected by cancellation of the request")
		}

		*//*val job2 = launch(coroutineContext) {
			delay(100)
			log("job2: I am child of the request coroutine")
			delay(1000)
			log("job2: I will not execute this line of my parent request is cancelled")
		}*//*

		job1.join()
//		job2.join()
	}
	delay(500)
	request.cancel()
	delay(1000)
	log("main: Who has survived request cancellation?")
}*/

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val request = launch {
		repeat(3) { i ->
			launch(coroutineContext) {
				delay((i + 1) * 200L)
				log("Coroutine $i is done")
			}
		}
		log("request: I'm done and I don't explicitly join my children that are still active")
	}
	request.join()
	log("Now processing of the request complete")
}*/

/*
fun main(args: Array<String>) = runBlocking<Unit>(CoroutineName("main")) {
	log("started main coroutine")
	val v1 = async(CoroutineName("v1coroutine")) {
		delay(500)
		log("Computing v1")
		252
	}

	val v2 = async(CoroutineName("v2coroutine")){
		delay(1000)
		log("Computing v2")
		6
	}

	log("the answer for v1 / v2 = ${v1.await() / v2.await()}")
}*/

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val job = Job()
	
	val coroutines = List(10) { index ->  
		launch(coroutineContext, parent = job ) {
			delay((1 + index) * 200L)
			log("Coroutine $index is done")
		}
	}

	log("Launched ${coroutines.size} coroutines")
	delay(500L)
	log("Cancelling the job")
	job.cancelAndJoin()
}*/
