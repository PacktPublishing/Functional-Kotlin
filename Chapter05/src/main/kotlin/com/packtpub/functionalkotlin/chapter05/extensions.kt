package com.packtpub.functionalkotlin.chapter05

fun String.sendToConsole() = println(this)

@JvmName("toConsole")
fun sendToConsole(string: String) = println(string)


/*class Human(private val name: String)

fun Human.speak(): String = "${this.name} makes a noise" //Cannot access 'name': it is private in 'Human'*/

open class Canine {
	open fun speak() = "<generic canine noise>"
}

class Dog : Canine() {
	override fun speak() = "woof!!"
}

fun printSpeak(canine: Canine) {
	println(canine.speak())
}

open class Feline

fun Feline.speak() = "<generic feline noise>"

class Cat : Feline()

fun Cat.speak() = "meow!!"

fun printSpeak(feline: Feline) {
	println(feline.speak())
}

open class Primate(val name: String)

fun Primate.speak() = "$name: <generic primate noise>"

open class GiantApe(name: String) : Primate(name)

fun GiantApe.speak() = "${this.name} :<scary 100db roar>"

fun printSpeak(primate: Primate) {
	println(primate.speak())
}


open class Caregiver(val name: String) {
	open fun Feline.react() = "PURRR!!!"

	private fun Primate.react() = "*$name plays with ${this@Caregiver.name}*"

	fun takeCare(feline: Feline) {
		println("Feline reacts: ${feline.react()}")
	}

	fun takeCare(primate: Primate) {
		println("Primate reacts: ${primate.react()}")
	}
}

open class Vet(name: String) : Caregiver(name) {
	override fun Feline.react() = "*runs away from $name*"
}

class Dispatcher {

	val dispatcher: Dispatcher = this

	fun String.extension() {
		val receiver: String = this
		val dispatcher: Dispatcher = this@Dispatcher

	}
}

class Worker {
	fun work() = "*working hard*"

	private fun rest() = "*resting*"
}

fun Worker.work() = "*not working so hard*"

fun <T> Worker.work(t:T) = "*working on $t*"

fun Worker.rest() = "*playing video games*"

object Builder {

}

fun Builder.buildBridge() = "A shinny new bridge"

class Designer {
	companion object {

	}

	object Desk {

	}
}


fun Designer.Companion.fastPrototype() = "Prototype"

fun Designer.Desk.portofolio() = listOf("Project1", "Project2")

class NormalClass {
	fun normalMethod() = println("NormalClass.normalMethod")

	infix fun infixFunction(i:Int) = println(i)
}

fun NormalClass.extension1() = println("NormalClass.extension1")

fun <T> T.extension1() = println("T.extension1")
fun <T> T.normalMethod() = println("T.normalMethod")
infix fun <T> T.infixFunction(i:Int) = println(i)




fun main(args: Array<String>) {
	/*"Hello world! (from an extension function)".sendToConsole()
	sendToConsole("Hello world! (from a normal function)")*/
	

	/*Designer.fastPrototype()
	Designer.Desk.portofolio().forEach(::println)*/

	/*printSpeak(Canine())
	printSpeak(Dog())*/

	/*printSpeak(Feline())
	printSpeak(Cat())*/

	/*printSpeak(Primate("Koko"))
	printSpeak(GiantApe("Kong"))*/

	/*val adam = Caregiver("Adam")

	val fulgencio = Cat()

	val koko = Primate("Koko")

	val brenda = Vet("Brenda")

	listOf(adam, brenda).forEach { caregiver ->
		println("${caregiver.javaClass.simpleName} ${caregiver.name}")
		caregiver.takeCare(fulgencio)
		caregiver.takeCare(koko)
	}*/

	/*val worker = Worker()

	println(worker.work())

	println(worker.work("refactoring"))

	println(worker.rest())*/

//	println(Builder.buildBridge())

	val normal = NormalClass()

	normal.extension1()

	normal.normalMethod()

	normal infixFunction 1
}

