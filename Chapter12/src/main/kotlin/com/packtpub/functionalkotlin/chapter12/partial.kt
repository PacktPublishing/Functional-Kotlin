package com.packtpub.functionalkotlin.chapter12

import arrow.core.PartialFunction
import arrow.core.orElse


/*
fun main(args: Array<String>) {
	val upper: (String?) -> String = { s: String? -> s!!.toUpperCase() } //Partial function, it can't transform null

	listOf("one", "two", null, "four").map(upper).forEach(::println)
}*/


/*
fun main(args: Array<String>) {

	val upper: (String?) -> String = { s: String? -> s!!.toUpperCase() } //Partial function, it can't transform null

	val partialUpper: PartialFunction<String?, String> = PartialFunction(definetAt = { s -> s != null }, f = upper)

	listOf("one", "two", null, "four").map(partialUpper).forEach(::println)
}*/

/*fun main(args: Array<String>) {

	val upper: (String?) -> String = { s: String? -> s!!.toUpperCase() } //Partial function, it can't transform null

	val partialUpper: PartialFunction<String?, String> = PartialFunction(definetAt = { s -> s != null }, f = upper)

	listOf("one", "two", null, "four").map{ s -> partialUpper.invokeOrElse(s, "NULL")}.forEach(::println)
}*/

/*fun main(args: Array<String>) {

	val upper: (String?) -> String = { s: String? -> s!!.toUpperCase() } //Partial function, it can't transform null

	val partialUpper: PartialFunction<String?, String> = PartialFunction(definetAt = { s -> s != null }, f = upper)

	val upperForNull: PartialFunction<String?, String> = PartialFunction({ s -> s == null }) { "NULL" }

	val totalUpper: PartialFunction<String?, String> = partialUpper orElse upperForNull

	listOf("one", "two", null, "four").map(totalUpper).forEach(::println)
}*/


fun main(args: Array<String>) {
	val fizz = PartialFunction({ n: Int -> n % 3 == 0 }) { "FIZZ" }
	val buzz = PartialFunction({ n: Int -> n % 5 == 0 }) { "BUZZ" }
	val fizzBuzz = PartialFunction({ n: Int -> fizz.isDefinedAt(n) && buzz.isDefinedAt(n) }) { "FUZZBIZZ" }
	val pass = PartialFunction({ true }) { n: Int -> n.toString() }

	(1..50).map(fizzBuzz orElse buzz orElse fizz orElse pass).forEach(::println)
}