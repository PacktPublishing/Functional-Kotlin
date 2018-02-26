package com.packtpub.functionalkotlin.chapter02

import com.packtpub.functionalkotlin.chapter02.FunList.Cons
import com.packtpub.functionalkotlin.chapter02.FunList.Nil

/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 5/11/17
 * Time: 2:21 AM
 */

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()

    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()

    fun forEach(f: (T) -> Unit) {
        tailrec fun go(list: FunList<T>, f: (T) -> Unit) {
            when (list) {
                is Cons -> {
                    f(list.head)
                    go(list.tail, f)
                }
                is Nil -> Unit//Do nothing
            }
        }

        go(this, f)
    }

    fun <R> fold(init: R, f: (R, T) -> R): R {
        tailrec fun go(list: FunList<T>, init: R, f: (R, T) -> R): R = when (list) {
            is Cons -> go(list.tail, f(init, list.head), f)
            is Nil -> init
        }

        return go(this, init, f)
    }

    fun reverse(): FunList<T> = fold(Nil as FunList<T>) { acc, i -> Cons(i, acc) }

    fun <R> foldRight(init: R, f: (R, T) -> R): R = this.reverse().fold(init, f)

    fun <R> map(f: (T) -> R): FunList<R> = foldRight(Nil as FunList<R>) { tail, head -> Cons(f(head), tail) }

}

fun intListOf(vararg numbers: Int): FunList<Int> {
    return if (numbers.isEmpty()) {
        Nil
    } else {
        Cons(numbers.first(), intListOf(*numbers.drop(1).toTypedArray().toIntArray()))
    }
}

/*
fun main(args: Array<String>) {

//	val numbers = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))

	val numbers = intListOf(1, 2, 3, 4)

	//println(numbers.reverse())

	*/
/*numbers.forEach { i -> println("i = $i") }*//*



	
	numbers.map { i -> i * 2 }.forEach(::println)

	val i = listOf(1, 1.0)

	*/
/*val funList = intListOf(1, 2, 3, 4)
	val list = listOf(1, 2, 3, 4)

	println("fold on funList : ${executionTime { funList.fold(0) { acc, i -> acc + i } }}")
	println("fold on list : ${executionTime { list.fold(0) { acc, i -> acc + i } }}")*//*

}*/

/*
fun main(args: Array<String>) {
    val numbers = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
}*/

/*
fun main(args: Array<String>) {
    val numbers = intListOf(1, 2, 3, 4)
}*/

/*
fun main(args: Array<String>) {
    val numbers = intListOf(1, 2, 3, 4)

    numbers.forEach { i -> println("i = $i") }
}*/

/*
fun main(args: Array<String>) {
    val numbers = intListOf(1, 2, 3, 4)

    val sum = numbers.fold(0) { acc, i -> acc + i}
}*/

/*
fun main(args: Array<String>) {
    val funList = intListOf(1, 2, 3, 4)
    val list = listOf(1, 2, 3, 4)

    println("fold on funList : ${executionTime { funList.fold(0) { acc, i -> acc + i } }}")
    println("fold on list : ${executionTime { list.fold(0) { acc, i -> acc + i } }}")
}*/
