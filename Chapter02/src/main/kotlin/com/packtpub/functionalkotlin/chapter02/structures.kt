package com.packtpub.functionalkotlin.chapter02


/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 7/10/17
 * Time: 1:53 AM
 */






fun main(args: Array<String>) {
//	println(capitalize("hello world!"))

	/*println(transform("kotlin", capitalize))

	println(transform("kotlin", ::reverse))

	val transformer = Transformer()

	println(transform("kotlin", transformer::upperCased))

	println(transform("kotlin", Transformer.Companion::lowerCased))

	println(transform("kotlin", MyUtils::doNothing))

	println(transform("kotlin", { str -> str.substring(0..1) }))
	println(transform("kotlin", { it.substring(0..1) }))
	println(transform("kotlin") { str -> str.substring(0..1) })


	val securityCheck = false

	unless(securityCheck) {
		println("You can't access this website")
	}


	useMachine(5, PrintMachine())

	useMachine(5, ::println)

	useMachine(5) { i ->
		println(i)
	}*/

	/*println("factorial :" + executionTime { factorial(20) })
	println("functionalFactorial :" + executionTime { functionalFactorial(20) })
	println("tailrecFactorial :" + executionTime { tailrecFactorial(20) })*/

	/*println("fib :" + executionTime { fib(93) })
	println("functionalFib :" + executionTime { functionalFib(93) })
	println("tailrecFib :" + executionTime { tailrecFib(93) })*/

//	val size = listOf(2 + 1, 3 * 2, 1 / 0, 5 - 4).size
//	val size = listOf({ 2 + 1 }, { 3 * 2 }, { 1 / 0 }, { 5 - 4 }).size
//	println(size)

	/*val a by lazy({ 2 + 1 })
	val b by lazy({ 3 * 2 })
	val c by lazy({ 1 / 0 })
	val d by lazy({ 5 - 4 })

	val size = listOf(a, b, c, d).size*/

	/*val i by lazy {
		println("Lazy evaluation")
		1
	}

	println("before using i")
	println(i)*/

	/*fun f(x: Long) : Long {
		return x * x
	}

	var i = 0

	fun g(x: Long): Long {
		i++
		return x * i // accessing mutable state
	}

	println(g(1)) //0
	i++
	println(g(1)) //1
	i++
	println(g(1)) //2*/

	val numbers: List<Int> = listOf(1, 2, 3, 4)

	val sum = numbers.foldRight(0) { acc, i ->
		println("acc, i = $acc, $i")
		acc + i
	}

	println(sum)




//	val numbersTwice: List<Int> = numbers.map { i -> i * 2 }


	/*val numbersTwice: MutableList<Int> = mutableListOf()

	for (i in numbers) {
		numbersTwice.add(i * 2)
	}*/


	/*for(i in numbers) {
		println("i = $i")
	}

	numbers.forEach { i -> println("i = $i") }*/


}