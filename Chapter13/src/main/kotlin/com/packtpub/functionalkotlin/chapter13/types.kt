package com.packtpub.functionalkotlin.chapter13

import arrow.HK
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.ev
import arrow.deriving
import arrow.higherkind
import arrow.instance
import arrow.typeclasses.Functor
import arrow.typeclasses.functor

@higherkind
class Mappable<T>(val t: T) : MappableKind<T> {
	fun <R> map(f: (T) -> R): Mappable<R> = Mappable(f(t))

	override fun toString(): String = "Mappable(t=$t)"

	companion object
}

@instance(Mappable::class)
interface MappableFunctorInstance : Functor<MappableHK> {
	override fun <A, B> map(fa: MappableKind<A>, f: (A) -> B): Mappable<B> {
		return fa.ev().map(f)
	}
}


@higherkind
@deriving(Functor::class)
class DerivedMappable<T>(val t: T) : DerivedMappableKind<T> {
	fun <R> map(f: (T) -> R): DerivedMappable<R> = DerivedMappable(f(t))

	override fun toString(): String = "DerivedMappable(t=$t)"

	companion object
}


@higherkind
class NotAFunctor<T>(val t: T) : NotAFunctorKind<T> {
	fun <R> map(f: (T) -> R): NotAFunctor<R> = NotAFunctor(f(t))

	override fun toString(): String = "NotAFunctor(t=$t)"
}


inline fun <reified F> buildBicycle(mapper: HK<F, Int>,
									noinline f: (Int) -> Bicycle,
									FR: Functor<F> = functor()): HK<F, Bicycle> = FR.map(mapper, f)

fun main(args: Array<String>) {

	val mappable: Mappable<Bicycle> = buildBicycle(Mappable(3), ::Bicycle).ev()
	println("mappable = $mappable")

	val derived: DerivedMappable<Bicycle> = buildBicycle(DerivedMappable(3), ::Bicycle).ev()
	println("derived = $derived")

	val option: Option<Bicycle> = buildBicycle(Some(2), ::Bicycle).ev()
	println("option = $option")

	val none: Option<Bicycle> = buildBicycle(None, ::Bicycle).ev()
	println("none = $none")

	val not: NotAFunctor<Bicycle> = buildBicycle(NotAFunctor(4), ::Bicycle).ev()
	println("not = $not")

}
