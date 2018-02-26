package com.packtpub.functionalkotlin.chapter02

/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 9/02/18
 * Time: 5:03 AM
 */
val capitalize = { str: String -> str.capitalize() }
/*val capitalize = object : Function1<String, String> {
	override fun invoke(p1: String): String {
		return p1.capitalize()
	}
}*/

fun <T> transform(t: T, fn: (T) -> T): T {
    return fn(t)
}

fun reverse(str: String): String {
    return str.reversed()
}

class Transformer {
    fun upperCased(str: String): String {
        return str.toUpperCase()
    }

    companion object {
        fun lowerCased(str: String): String {
            return str.toLowerCase()
        }
    }
}

object MyUtils {
    fun doNothing(str: String): String {
        return str
    }
}

fun unless(condition: Boolean, block: () -> Unit) {
    if (!condition) block()
}

/*interface Machine<T> {
	fun process(t: T)
}

fun <T> useMachine(t: T, machine: Machine<T>) {
   machine.process(t)
}

class PrintMachine<T> : Machine<T> {
   override fun process(t: T) {
      println(t)
   }
}*/


typealias Machine<T> = (T) -> Unit

fun <T> useMachine(t: T, machine: Machine<T>) {
    machine(t)
}

class PrintMachine<T> : Machine<T> {
    override fun invoke(p1: T) {
        println(p1)
    }
}

/*
fun main(args: Array<String>) {
    println(transform("kotlin", capitalize))
}*/

/*
fun main(args: Array<String>) {
    println(transform("kotlin", ::reverse))
}*/


/*
fun main(args: Array<String>) {
    println(transform("kotlin", MyUtils::doNothing))
}*/

/*
fun main(args: Array<String>) {
    val transformer = Transformer()

    println(transform("kotlin", transformer::upperCased))

    println(transform("kotlin", Transformer.Companion::lowerCased))
}*/

/*fun main(args: Array<String>) {
    println(transform("kotlin", { str -> str.substring(0..1) }))
}*/

/*fun main(args: Array<String>) {
    println(transform("kotlin", { it.substring(0..1) }))
}*/

/*fun main(args: Array<String>) {
    println(transform("kotlin") { str -> str.substring(0..1) })
}*/

/*fun main(args: Array<String>) {
    val securityCheck = false // some interesting code here

    unless(securityCheck) {
        println("You can't access this website")
    }
}*/


/*
fun main(args: Array<String>) {
    useMachine(5, PrintMachine())

    useMachine(5, object : Machine<Int> {
       override fun process(t: Int) {
          println(t)
       }
    })
}*/

fun main(args: Array<String>) {
    useMachine(5, PrintMachine())

    useMachine(5, ::println)

    useMachine(5) { i ->
        println(i)
    }
}