package com.packtpub.functionalkotlin.chapter12

import arrow.core.PartialFunction
import arrow.core.constant
import arrow.core.orElse

/*
fun main(args: Array<String>) {

	val oneToFour = 1..4

	println("With identity: ${oneToFour.map(::identity).joinToString()}") //1, 2, 3, 4

	println("With constant: ${oneToFour.map(constant(1)).joinToString()}") //1, 1, 1, 1
	
}*/

fun main(args: Array<String>) {
	val fizz = PartialFunction({ n: Int -> n % 3 == 0 }, constant("FIZZ"))
	val buzz = PartialFunction({ n: Int -> n % 5 == 0 }, constant("BUZZ"))
	val fizzBuzz = PartialFunction({ n: Int -> fizz.isDefinedAt(n) && buzz.isDefinedAt(n) }, constant("FIZZBUZZ"))
	val pass = PartialFunction<Int, String>(constant(true)) { n -> n.toString() }

	(1..50).map(fizzBuzz orElse buzz orElse fizz orElse pass).forEach(::println)
}