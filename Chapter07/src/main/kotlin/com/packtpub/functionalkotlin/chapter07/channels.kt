package com.packtpub.functionalkotlin.chapter07

import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.channels.*
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.coroutines.experimental.CoroutineContext


/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val channel = Channel<Int>()
	launch {
		for (x in 1..5) channel.send(x * x)
	}

	repeat(5) {
		log(channel.receive().toString())
	}
	log("Done")
}*/

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val channel = Channel<Int>()
	launch {
		for (x in 1..5) channel.send(x * x)
		channel.close()
	}

	for (y in channel){
		log(y.toString())
	}
	log("Done")
}*/

/*
fun produceSquares() = produce {
	for (x in 1..5) {
		send(x * x)
	}
}

fun main(args: Array<String>) = runBlocking<Unit> {
	val squares = produceSquares()
	squares.consumeEach { log(it.toString()) }
	log("Done!")
}*/

fun produceNumbers() = produce {
	var x = 1
	while (true) {
		send(x++)
//		delay(1)
	}
}

fun square(numbers: ReceiveChannel<Int>) = produce {
	for (x in numbers) send(x * x)
}


/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val numbers = produceNumbers()
	val squares = square(numbers)
	repeat(5) {
		log(squares.receive().toString())
	}
	log("Done")
	numbers.cancel()
	squares.cancel()
}*/


fun numbersFrom(context: CoroutineContext, start: Int) = produce(context) {
	var x = start
	while (true) send(x++)
}

fun filter(context: CoroutineContext, numbers: ReceiveChannel<Int>, prime: Int) = produce(context) {
	for (x in numbers) if (x % prime != 0) send(x)
}

/*fun main(args: Array<String>) = runBlocking<Unit> {
	var current = numbersFrom(coroutineContext, 2)
	repeat(10) {
		val prime = current.receive()
		log(prime)
		current = filter(coroutineContext, current, prime)
	}
	coroutineContext.cancelChildren()
}*/

fun launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
	channel.consumeEach {
		log("Processor #$id received $it")
	}
}

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val producer = produceNumbers()
	repeat(5){
		launchProcessor(it, producer)
	}
	delay(950)
	producer.cancel()
}*/

suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
	while (true) {
		delay(time)
		channel.send(s)
	}
}

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val channel = Channel<String>()
	launch(coroutineContext) {
		sendString(channel, "foo", 200L)
	}
	launch(coroutineContext) {
		sendString(channel, "BAR!", 500L)
	}
	repeat(6) {
		log(channel.receive())
	}
	coroutineContext.cancelChildren()
}*/

/*
fun main(args: Array<String>) = runBlocking<Unit> {
	val channel = Channel<Int>(4)
	val sender = launch(coroutineContext) {
		repeat(10) {
			log("Sending $it")
			channel.send(it)
		}
	}
	delay(1000)
	sender.cancel()
}*/

data class Ball(var hits: Int)

suspend fun player(name: String, table: Channel<Ball>) {
	for (ball in table) {
		ball.hits++
		log("$name $ball")
		delay(300)
		table.send(ball)
	}
}
/*

fun main(args: Array<String>) = runBlocking<Unit> {
	val table = Channel<Ball>()
	launch(coroutineContext) { player("ping", table) }
	launch(coroutineContext) { player("pong", table) }
	table.send(Ball(0))
	delay(1000)
	coroutineContext.cancelChildren()
}*/

/*fun main(args: Array<String>) = runBlocking {
	 val result = CompletableDeferred<String>()

	val world = launch {
		delay(500)
		result.complete("World (from another coroutine)")
	}

	val hello =launch {
		println("Hello ${result.await()}")
	}

	hello.join()
	world.join()
}*/

/*fun main(args: Array<String>) = runBlocking<Unit> {
	val channel = Channel<String>()

	val world = launch {
		delay(500)
		channel.send("World (from another coroutine using a channel)")
	}

	val hello = launch {
		println("Hello ${channel.receive()}")
	}

	hello.join()
	world.join()
}*/


/*fun main(args: Array<String>) = runBlocking<Unit> {

	val channel = Channel<Char>()

	val sender = launch {
		repeat(1000) {
			delay(10)
			channel.send('.')
			delay(10)
			channel.send(',')
		}
		channel.close()
	}


	for (msg in channel) {
		print(msg)
	}

	sender.join()

}*/

/*fun dotsAndCommas(size: Int) = produce {
	repeat(size) {
		delay(10)
		send('.')
		delay(10)
		send(',')
	}
}

fun main(args: Array<String>) = runBlocking<Unit> {
	val channel = dotsAndCommas(1000)

	for (msg in channel) {
		print(msg)
	}
}*/

data class Quote(val value: Double, val client: String, val item: String, val quantity: Int)

data class Bill(val value: Double, val client: String)

data class PickingOrder(val item: String, val quantity: Int)


fun calculatePriceTransformer(coroutineContext: CoroutineContext, quoteChannel: ReceiveChannel<Quote>) = produce(coroutineContext) {
	for (quote in quoteChannel) {
		send(Bill(quote.value * quote.quantity, quote.client) to PickingOrder(quote.item, quote.quantity))
	}
}

fun cheapBillFilter(coroutineContext: CoroutineContext, billChannel: ReceiveChannel<Pair<Bill, PickingOrder>>) = produce(coroutineContext) {
	billChannel.consumeEach { (bill, order) ->
		if (bill.value >= 100) {
			send(bill to order)
		} else {
			println("Discarded bill $bill")
		}
	}
}

suspend fun splitter(filteredChannel: ReceiveChannel<Pair<Bill, PickingOrder>>,
					 accountingChannel: SendChannel<Bill>,
					 warehouseChannel: SendChannel<PickingOrder>) = launch {
	filteredChannel.consumeEach { (bill, order) ->
		accountingChannel.send(bill)
		warehouseChannel.send(order)
	}
}

suspend fun accountingEndpoint(accountingChannel: ReceiveChannel<Bill>) = launch {
	accountingChannel.consumeEach { bill ->
		println("Processing bill = $bill")
	}
}

suspend fun warehouseEndpoint(warehouseChannel: ReceiveChannel<PickingOrder>) = launch {
	warehouseChannel.consumeEach { order ->
		println("Processing order = $order")
	}
}


fun main(args: Array<String>) = runBlocking {

	val quoteChannel = Channel<Quote>()
	val accountingChannel = Channel<Bill>()
	val warehouseChannel = Channel<PickingOrder>()

	val transformerChannel = calculatePriceTransformer(coroutineContext, quoteChannel)

	val filteredChannel = cheapBillFilter(coroutineContext, transformerChannel)

	splitter(filteredChannel, accountingChannel, warehouseChannel)

	warehouseEndpoint(warehouseChannel)

	accountingEndpoint(accountingChannel)

	launch(coroutineContext) {
		quoteChannel.send(Quote(20.0, "Foo", "Shoes", 1))
		quoteChannel.send(Quote(20.0, "Bar", "Shoes", 200))
		quoteChannel.send(Quote(2000.0, "Foo", "Motorbike", 1))
	}
	
	delay(1000)
	coroutineContext.cancelChildren()
}
