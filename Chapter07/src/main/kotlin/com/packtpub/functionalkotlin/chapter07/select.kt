package com.packtpub.functionalkotlin.chapter07

import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.SendChannel
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.selects.select
import kotlin.coroutines.experimental.CoroutineContext

fun fizz(context: CoroutineContext) = produce(context) {
	while (true) {
		delay(300)
		send("Fizz")
	}
}

fun buzz(context: CoroutineContext) = produce(context) {
	while (true) {
		delay(500)
		send("Buzz")
	}
}

suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {
	select<Unit> {
		fizz.onReceive {
			log("fizz -> $it")
		}

		buzz.onReceive {
			log("buzz -> $it")
		}
	}
}

/*fun main(args: Array<String>) = runBlocking<Unit> {
	val fizz = fizz(coroutineContext)
	val buzz = buzz(coroutineContext)
	repeat(7) {
		selectFizzBuzz(fizz, buzz)
	}
	coroutineContext.cancelChildren()
}*/

suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>): String = select {
	fun receive(channel: ReceiveChannel<String>, name: String) {
		channel.onReceiveOrNull {
			if (it == null) {
				"Channel '$name' is closed"
			} else {
				"$name -> $it"
			}
		}
	}

	receive(a, "a")
	receive(b, "b")
}

/*fun main(args: Array<String>) = runBlocking<Unit> {
	val a = produce(coroutineContext) {
		repeat(4) { send("Hello $it") }
	}

	val b = produce(coroutineContext) {
		repeat(4) { send("World $it") }
	}

	repeat(8){
		log(selectAorB(a, b))
	}
	coroutineContext.cancelChildren()
}*/

fun produceNumbers(context: CoroutineContext, side:SendChannel<Int>) = produce<Int>(context) {
	for (i in 1..10) {
		delay(100)
		select<Unit> {
			onSend(i){}
			side.onSend(i){}
		}
	}
}

/*fun main(args: Array<String>) = runBlocking<Unit> {
	val side = Channel<Int>()
	launch(coroutineContext) {
		side.consumeEach { log("Side channel has $it") }
	}
	produceNumbers(coroutineContext, side).consumeEach {
		log("Consuming $it")
		delay(250)
	}
	log("Done consuming")
	coroutineContext.cancelChildren()
}*/
