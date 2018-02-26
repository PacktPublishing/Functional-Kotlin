package com.packtpub.functionalkotlin.chapter13

import arrow.core.*
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.syntax.option.toEither
import arrow.typeclasses.binding


fun randomlyEither(): Either<String, Int> = randomlyOption().toEither { "Nothing here" }

/*
fun main(args: Array<String>) {
	val either = randomlyEither()
	when(either){
		is Either.Right -> println("Int: ${either.b}")
		is Either.Left -> println(either.a)
	}
}*/

/*
fun main(args: Array<String>) {
	randomlyEither().fold(::println,{i -> println("Int: $i")})
}*/

/*
fun main(args: Array<String>) {
	randomlyEither().map { i -> "Int: $i" }.fold(::println, ::println)
}*/

/*
fun main(args: Array<String>) {
	randomlyEither().flatMap {  i ->
		if (i > 0) {
			Right(i)
		} else {
			Left("Not a positive")
		}
	}.getOrElse { 0 } pipe ::println
}*/


fun main(args: Array<String>) {
    Either.monad<String>().binding {
        val num1 = randomlyEither().bind()
        val num2 = randomlyEither().mapLeft { s -> s.toUpperCase() }.bind()
        num1 + num2
    }.ev().fold(::println, ::println)
}

fun eitherDivide(num: Int, den: Int): Either<String, Int> {
    val option = optionDivide(num, den)
    return when (option) {
        is Some -> Right(option.t)
        None -> Left("$num isn't divisible by $den")
    }
}

fun eitherDivision(a: Int, b: Int, den: Int): Either<String, Tuple2<Int, Int>> {
    val aDiv = eitherDivide(a, den)
    return when (aDiv) {
        is Right -> {
            val bDiv = eitherDivide(b, den)
            when (bDiv) {
                is Right -> Right(aDiv.getOrElse { 0 } toT bDiv.getOrElse { 0 })
                is Left -> bDiv as Either<String, Nothing>
            }
        }
        is Left -> aDiv as Either<String, Nothing>
    }
}

fun flatMapEitherDivision(a: Int, b: Int, den: Int): Either<String, Tuple2<Int, Int>> {
    return eitherDivide(a, den).flatMap { aDiv ->
        eitherDivide(b, den).flatMap { bDiv ->
            Right(aDiv toT bDiv)
        }
    }
}

//
fun comprehensionEitherDivision(a: Int, b: Int, den: Int): Either<String, Tuple2<Int, Int>> {
    return Either.monad<String>().binding {
        val aDiv = eitherDivide(a, den).bind()
        val bDiv = eitherDivide(b, den).bind()
        aDiv toT bDiv
    }.ev()
}

/*fun main(args: Array<String>) {
	eitherDivision(3, 2, 4).fold(::println, ::println)
}*/



