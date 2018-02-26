package com.packtpub.functionalkotlin.chapter13

import arrow.core.*
import arrow.syntax.option.toOption
import arrow.typeclasses.binding
import java.util.*

fun randomlyNull(): Int? {
	val random = Random()
	return if (random.nextBoolean()) {
		random.nextInt()
	} else {
		null
	}
}

/*fun randomlyOption(): Option<Int> {
	val random = Random()
	return if(random.nextBoolean()){
		Some(random.nextInt())
	} else {
		None
	}
}*/

fun randomlyOption(): Option<Int> = randomlyNull().toOption()


/*fun main(args: Array<String>) {
	val randomlyOption = randomlyOption()
	if (randomlyOption.isDefined()) {
		println(randomlyOption.t)
	} else{
		println("Nothing here")
	}
}*/


/*fun main(args: Array<String>) {
	val randomlyString: Option<String> = randomlyOption().map { i -> i.toString() }
	randomlyString.getOrElse { "Nothing here" } pipe ::println
}*/

/*


fun main(args: Array<String>) {
	val randomlyPositive: Option<Int> = randomlyOption().flatMap { i ->
			if (i > 0) {
				Some(i)
			} else {
				None
			}
		}
}*/


/*fun main(args: Array<String>) {
	randomlyOption().fold({ "Nothing here" }, ::identity) pipe ::println
}*/


data class Bicycle(val gears:Int)

/*fun main(args: Array<String>) {

	val SG: Semigroup<Bicycle> = object : Semigroup<Bicycle> {
		override fun combine(a: Bicycle, b: Bicycle): Bicycle {
			return if (a.gears > b.gears) {
				a
			} else {
				b
			}
		}
	}
	val fromSemi = Option.semigroup(SG).combine(Option.pure(Bicycle(22)), Option.pure(Bicycle(11)))
	println(fromSemi)

	val fromMonoid = Option.monoid(SG).combineAll(Option.pure(Bicycle(12)), None)

	println(fromMonoid)

	val tuple =Option.applicative().product(Option(Bicycle(22)),Option(Bicycle(11))).ev()

	println(tuple)
	
	val bicycles =listOf(11,22,9,1).k().traverse({i -> Bicycle(i).toOption()}, Option.applicative()).ev()
	bicycles.fold({"No bicycles"}){ bs ->
		bs.joinToString()
	} pipe ::println
}*/

/*fun main(args: Array<String>) {
	 randomlyOption().fold({ println("Nothing here") }, ::println)
}*/

fun divide(num: Int, den: Int): Int? {
	return if (num % den != 0) {
		null
	} else {
		num / den
	}
}

fun division(a: Int, b: Int, den: Int): Pair<Int, Int>? {
	val aDiv = divide(a, den)
	return when (aDiv) {
		is Int -> {
			val bDiv = divide(b, den)
			when (bDiv) {
				is Int -> aDiv to bDiv
				else -> null
			}
		}
		else -> null
	}
}

fun optionDivide(num: Int, den: Int): Option<Int> = divide(num, den).toOption()

fun optionDivision(a: Int, b: Int, den: Int): Option<Pair<Int, Int>> {
	val aDiv = optionDivide(a, den)
	return when (aDiv) {
		is Some -> {
			val bDiv = optionDivide(b, den)
			when (bDiv) {
				is Some -> Some(aDiv.t to bDiv.t)
				else -> None
			}
		}
		else -> None
	}
}

fun flatMapDivision(a: Int, b: Int, den: Int): Option<Pair<Int, Int>> {
	return optionDivide(a, den).flatMap { aDiv: Int ->
		optionDivide(b, den).flatMap { bDiv: Int ->
			Some(aDiv to bDiv)
		}
	}
}

fun comprehensionDivision(a: Int, b: Int, den: Int): Option<Pair<Int, Int>> {
	return Option.monad().binding {
		val aDiv: Int = optionDivide(a, den).bind()
		// start continuation 1
			val bDiv: Int = optionDivide(b, den).bind()
		    //start continuation 2
				aDiv to bDiv
			//end continuation 2
		// end continuation 1
	}.ev()
}

fun main(args: Array<String>) {
	println("division ${division(4, 2, 2)}")
	println("option division ${optionDivision(4, 2, 2)}")
	println("flatMap division ${flatMapDivision(4, 2, 2)}")
	println("comprehension division ${comprehensionDivision(4, 2, 2)}")
}




/*
fun main(args: Array<String>) {
	Option.functor().map(1.toOption()) { i -> i + 1 }.ev()

	Option.applicative().ap(1.toOption(), Some { i: Int -> i + 1 }).ev()

	Option.monad().flatMap(1.toOption()) { i -> Some(i + 1) }
}*/
