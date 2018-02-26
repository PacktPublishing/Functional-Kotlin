package com.packtpub.functionalkotlin.chapter07

import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.system.measureTimeMillis


suspend fun massiveRun(action: suspend () -> Unit) {
	val n = 1000
	val k = 1000
	val time = measureTimeMillis {
		val jobs = List(n) {
			launch {
				repeat(k) { action() }
			}
		}
		jobs.forEach { it.join() }
	}
	log("Completed ${n * k} actions in $time ms")
}

/*fun main(args: Array<String>) = runBlocking<Unit> {
	//	val counter = AtomicInteger()
	var counter = 0
	val mutex = Mutex()
	massiveRun {
		//		counter.incrementAndGet()
		mutex.withLock {
			counter++
		}
	}
//	log("Counter = ${counter.get()}")
	log("Counter = $counter")
}*/

sealed class CounterMsg
object IncCounter : CounterMsg()
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

fun counterActor(start:Int) = actor<CounterMsg> {
	var counter = start
	for (msg in channel) {
		when (msg) {
			is IncCounter -> counter++
			is GetCounter -> msg.response.complete(counter)
		}
	}
}

fun main(args: Array<String>) = runBlocking<Unit> {
	val counter = counterActor(0)
	massiveRun{
		counter.send(IncCounter)
	}
	val response = CompletableDeferred<Int>()
	counter.send(GetCounter(response))
	log("Counter = ${response.await()}")
	counter.close()
}


suspend fun repeatInParallel(times: Int, block: suspend () -> Unit) {
	val job = launch {
		repeat(times) {
			launch(coroutineContext) {
				block()
			}
		}
	}
	job.join()
}

/*fun main(args: Array<String>) = runBlocking {
	var counter = 0

	val time = measureTimeMillis {
		repeatInParallel(1_000_000) {
			counter++
		}
	}
	println("counter = $counter")
	println("time = $time")
}*/


/*fun main(args: Array<String>) = runBlocking {
	val counter = AtomicInteger(0)

	val time = measureTimeMillis {
		repeatInParallel(1_000_000) {
			counter.incrementAndGet()
		}
	}
	println("counter = ${counter.get()}")
	println("time = $time")
}*/

/*fun main(args: Array<String>) = runBlocking {
	var counter = 0

	val counterContext = newSingleThreadContext("CounterContext")

	val time = measureTimeMillis {
		repeatInParallel(1_000_000) {
			withContext(counterContext) {
				counter++
			}
		}
	}
	println("counter = $counter")
	println("time = $time")
}*/

/*fun main(args: Array<String>) = runBlocking {
	val mutex = Mutex()
	var counter = 0

	val time = measureTimeMillis {
		repeatInParallel(1_000_000) {
			mutex.withLock {
				counter++
			}
		}
	}
	println("counter = $counter")
	println("time = $time")
}*/

/*fun main(args: Array<String>) = runBlocking {
	val counterActor = counterActor(0)

	val time = measureTimeMillis {
		repeatInParallel(1_000_000) {
			counterActor.send(IncCounter)
		}
	}
	
	val counter = CompletableDeferred<Int>()
	counterActor.send(GetCounter(counter))
	println("counter = ${counter.await()}")
	println("time = $time")
}*/



