package com.packtpub.functionalkotlin.chapter13

import arrow.core.Tuple2
import arrow.core.toT
import arrow.data.*
import arrow.data.Try.Success
import arrow.typeclasses.binding
import arrow.typeclasses.bindingCatch
import java.lang.IllegalArgumentException
import java.util.*

/*fun main(args: Array<String>) {
	try {
		val order = OrderService.getOrder("foo")
		println("order = $order")
	} catch (e: IllegalArgumentException) {
		println("Exception reading order :${e.message}")
	}
}*/

/*fun main(args: Array<String>) {
	Try { OrderService.getOrder("foo") }
			.getOrDefault { Order("", 0.0) } pipe ::println
}*/

/*fun main(args: Array<String>) {
	Try { OrderService.getOrder("foo") }
			.getOrElse { e: Throwable -> Order(e.message!!, 0.0) } pipe ::println
}*/


/*fun main(args: Array<String>) {
	Try { OrderService.getOrder("foo") }.filter { order -> order.price > 10 } pipe ::println
}*/

/*fun main(args: Array<String>) {
	Try { OrderService.getOrder("foo")}.recover { e -> e.message } pipe ::println
}*/

/*fun main(args: Array<String>) {
	Try { OrderService.getOrder("foo")}.map { order -> order.price } pipe ::println
}*/


fun main(args: Array<String>) {
	val totalPrice = Try.monad().binding {
		val fooPrice = Try { OrderService.getOrder("foo")}.map { order -> order.price }.bind()
		val bar = Try { OrderService.getOrder("bar") }.bind()
		fooPrice + bar.price

	}.ev()

	println(totalPrice.getOrDefault { -1 })
}

/*fun main(args: Array<String>) {
	val totalPrice = Try.monadError().bindingCatch{
		val fooPrice = OrderService.getOrder("foo").price
		val bar = OrderService.getOrder("bar")
		fooPrice + bar.price
	}.ev()
//
	println(totalPrice.getOrDefault { -1 })
}*/

data class Order(val serial: String, val price: Double)

object OrderService {
	private val random = Random()

	@Throws(IllegalArgumentException::class)
	fun getOrder(serial: String): Order {
		if (random.change(50)) {
			return Order(serial, 9.0)
		} else {
			throw IllegalArgumentException("Invalid serial:$serial")
		}
	}
}


fun tryDivide(num: Int, den: Int): Try<Int> = Try { divide(num, den)!! }

fun tryDivision(a: Int, b: Int, den: Int): Try<Tuple2<Int, Int>> {
	val aDiv = tryDivide(a, den)
	return when (aDiv) {
		is Success -> {
			val bDiv = tryDivide(b, den)
			when (bDiv) {
				is Success -> {
					Try { aDiv.value toT bDiv.value }
				}

				is Failure -> Failure(bDiv.exception)
			}
		}
		is Failure -> Failure(aDiv.exception)
	}
}

fun flatMapTryDivision(a: Int, b: Int, den: Int): Try<Tuple2<Int, Int>> {
	return tryDivide(a, den).flatMap { aDiv ->
		tryDivide(b, den).flatMap { bDiv ->
			Try { aDiv toT bDiv }
		}
	}
}

fun comprehensionTryDivision(a: Int, b: Int, den: Int): Try<Tuple2<Int, Int>> {
	return Try.monad().binding {
		val aDiv = tryDivide(a, den).bind()
		val bDiv = tryDivide(b, den).bind()
		aDiv toT bDiv
	}.ev()
}


fun monadErrorTryDivision(a: Int, b: Int, den: Int): Try<Tuple2<Int, Int>> {
	return Try.monadError().bindingCatch {
		val aDiv = divide(a, den)!!
		val bDiv = divide(b, den)!!
		aDiv toT bDiv
	}.ev()
}

