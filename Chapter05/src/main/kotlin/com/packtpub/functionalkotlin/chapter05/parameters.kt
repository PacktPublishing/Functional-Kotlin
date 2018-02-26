package com.packtpub.functionalkotlin.chapter05

/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 10/11/17
 * Time: 2:29 AM
 */

fun basicFunction(name: String, size: Int) {

}

data class Person(val name: String)

fun aVarargFun(vararg names: String) {
	names.forEach(::println)
}

fun high(f: (age:Int, name:String) -> Unit) {
	f(1, "Romeo")
}

/*fun multipleVarargs(vararg names: String, vararg sizes: Person) {
//	Error:(18, 43) Kotlin: Multiple vararg-parameters are prohibited
}*/

fun paramAfterVararg(courseId: Int, vararg students: String, roomTemperature: Double) {

}

fun <T, R> transform(vararg ts: T, f: (T) -> R): List<R> = ts.map(f)

fun <T> emit(t: T, vararg listeners: (T) -> Unit) = listeners.forEach { listener ->
	listener(t)
}

fun item(price: Double, vat: Double, total: Double = price + vat) {

}

fun main(args: Array<String>) {

	high{ q, w ->
		//do something
	}

	aVarargFun()
	aVarargFun("Angela", "Brenda", "Caroline")


	transform(1, 2, 3, 4) { i -> i.toString() }

    //emit(1){i -> println(i)} // Compilation error

	emit(1, ::println, { i -> println(i * 2) })

	paramAfterVararg(68, "Abel", "Barbara", "Carl", "Diane", roomTemperature = 18.0)

	val customer1 = Customer("John", "Carl", "Doe", "XX234", 82.3, 180)

	val customer2 = Customer(
			lastName = "Doe",
			firstName = "John",
			middleName = "Carl",
			height = 180,
			weight = 82.3,
			passportNumber = "XX234")

	val programmer1 = Programmer("John", "Doe")

//	val programmer2 = Programmer("John", "Doe", 12) //Error

	val programmer2 = Programmer("John", "Doe", yearsOfExperience = 12) //OK

	val programmer3 = Programmer("John", "Doe", "TypeScript", 1)
}

data class Programmer(val firstName: String,
					  val lastName: String,
					  val favouriteLanguage: String = "Kotlin",
					  val yearsOfExperience: Int = 0)

typealias Kg = Double
typealias cm = Int

data class Customer(val firstName: String,
					val middleName: String,
					val lastName: String,
					val passportNumber: String,
					val weight: Kg,
					val height: cm)