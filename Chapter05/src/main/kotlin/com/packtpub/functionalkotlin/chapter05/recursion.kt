package com.packtpub.functionalkotlin.chapter05

sealed class IntList<F>
class IntCons<F>(val head: Int, val tail: F) : IntList<F>()
object IntNil : IntList<Nothing>()

fun <T, R> IntList<T>.map(f: (T) -> R) : IntList<R> = when(this){
	is IntCons -> IntCons(head, f(tail))
	is IntNil -> IntNil as IntList<R>
}
