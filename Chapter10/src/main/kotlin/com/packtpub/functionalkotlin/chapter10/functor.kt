package com.packtpub.functionalkotlin.chapter10


sealed class Option<out T> {
    object None : Option<Nothing>() {
        override fun toString() = "None"
    }

    data class Some<out T>(val value: T) : Option<T>()

    companion object
}

/*
interface Functor<C<_>>{ //Invalid Kotlin code
    fun <A,B> map(ca:C<A>, transform:(A) -> B): C<B>
}

interface Monad<C<_>>: Functor<C>{
    fun <A, B> flatMap(ca:C<A>, fm:(A) -> C<B>): C<B>
}

interface Monad<C<_>>: Applicative<C>{
    fun <A, B> flatMap(ca:C<A>, fm:(A) -> C<B>): C<B>
}

interface Applicative<C<_>>: Functor<C> {
    fun <A> pure(a:A): C<A>

    fun <A, B> ap(ca:C<A>, fab: C<(A) -> B>): C<B>
}

interface Monad<C<_>>: Applicative<C>{
    fun <A, B> flatMap(ca:C<A>, fm:(A) -> C<B>): C<B>
}

*/

/*fun <T, R> Option<T>.map(transform: (T) -> R): Option<R> = when (this) {
    Option.None -> Option.None
    is Option.Some -> Option.Some(transform(value))
}*/


//Option
fun <T, R> Option<T>.map(transform: (T) -> R): Option<R> = flatMap { t -> Option.Some(transform(t)) }

fun <T, R> Option<T>.flatMap(fm: (T) -> Option<R>): Option<R> = when (this) {
    Option.None -> Option.None
    is Option.Some -> fm(value)
}
//fun <T, R> Option<T>.map(transform: (T) -> R): Option<R> = ap(Option.pure(transform))

fun <T> Option.Companion.pure(t: T): Option<T> = Option.Some(t)

fun <T, R> Option<T>.ap(fab: Option<(T) -> R>): Option<R> = fab.flatMap { f -> map(f) }

infix fun <T, R> Option<(T) -> R>.`(*)`(o: Option<T>): Option<R> = flatMap { f: (T) -> R -> o.map(f) }

//Function
fun <A, B, C> ((A) -> B).map(transform: (B) -> C): (A) -> C = { t -> transform(this(t)) }

fun <A, B, C> ((A) -> B).flatMap(fm: (B) -> (A) -> C): (A) -> C = { t -> fm(this(t))(t) }

fun <A, B, C> ((A) -> B).ap(fab: (A) -> (B) -> C): (A) -> C = fab.flatMap { f -> map(f) }


object Function1 {
    fun <A, B> pure(b: B) = { _: A -> b }
}

//List
fun <T, R> List<T>.ap(fab: List<(T) -> R>): List<R> = fab.flatMap { f -> this.map(f) }


fun calculateDiscount(price: Option<Double>): Option<Double> {
    return price.flatMap { p ->
        if (p > 50.0) {
            Option.Some(5.0)
        } else {
            Option.None
        }
    }
}


/*fun calculateFinalPrice(price: Option<Double>): Option<Double> {
    val discount = calculateDiscount(price)
    return price.flatMap { p ->
        discount.flatMap { d ->
            Option.Some(p - d)
        }
    }
}*/

/*fun calculateFinalPrice(price: Option<Double>): Option<Double> {
    val discount = calculateDiscount(price)
    return discount.ap(price.map { p: Double -> { d: Double -> p - d } })
}

fun main(args: Array<String>) {
    println(calculateFinalPrice(Option.Some(80.0))) //Some(value=5.0)
    println(calculateFinalPrice(Option.Some(30.0))) //None
    println(calculateFinalPrice(Option.None)) //None
}*/


/*fun main(args: Array<String>) {
    val maybeFive = Option.Some(5)
    val maybeTwo = Option.Some(2)

    println(maybeFive.flatMap { f ->
        maybeTwo.flatMap { t ->
            Option.Some(f + t)
        }
    }) // Some(value=7)
}*/

/*fun main(args: Array<String>) {
    val maybeFive = Option.Some(5)
    val maybeTwo = Option.Some(2)

    println(maybeFive.flatMap { f ->
        maybeTwo.map { t ->
            f + t
        }
    }) // Some(value=7)
}*/

/*fun main(args: Array<String>) {
    val maybeFive = Option.pure(5)
    val maybeTwo = Option.pure(2)

    println(maybeTwo.ap(maybeFive.map { f -> { t: Int -> f + t } })) // Some(value=7)
}*/

/*fun main(args: Array<String>) {
    val maybeFive = Option.pure(5)
    val maybeTwo = Option.pure(2)

    println(Option.pure { f: Int -> { t: Int -> f + t } } `(*)` maybeFive `(*)` maybeTwo) // Some(value=7)
}*/

/*fun main(args: Array<String>) {
    println(calculateDiscount(Option.Some(80.0))) //Some(value=5.0)
    println(calculateDiscount(Option.Some(30.0))) //None
    println(calculateDiscount(Option.None)) //None
}*/


/*fun main(args: Array<String>) {
    println(Option.Some("Kotlin")
            .map(String::toUpperCase))
}*/

/*fun main(args: Array<String>) {
    println(Option.Some("Kotlin").map(String::toUpperCase))
    println(Option.None.map(String::toUpperCase))
}*/

/*fun main(args: Array<String>) {
    val add3AndMultiplyBy2: (Int) -> Int = { i: Int -> i + 3 }.map { j -> j * 2 }
    println(add3AndMultiplyBy2(0)) //6
    println(add3AndMultiplyBy2(1)) //8
    println(add3AndMultiplyBy2(2)) //10
}*/


/*fun main(args: Array<String>) {
    val result = listOf(1, 2, 3)
            .flatMap { i ->
                listOf(i * 2, i + 3)
            }
            .joinToString()

    println(result) //2, 4, 4, 5, 6, 6
}*/

/*fun main(args: Array<String>) {
    val numbers = listOf(1, 2, 3)
    val functions = listOf<(Int) -> Int>({ i -> i * 2 }, { i -> i + 3 })
    val result = numbers.flatMap { number ->
        functions.map { f -> f(number) }
    }.joinToString()

    println(result) //2, 4, 4, 5, 6, 6
}*/

/*
fun main(args: Array<String>) {
    println(Option.pure(2).ap(Option.pure { i: Int -> i + 3 }))
}*/

/*fun main(args: Array<String>) {
    val numbers = listOf(1, 2, 3)
    val functions = listOf<(Int) -> Int>({ i -> i * 2 }, { i -> i + 3 })
    val result = numbers
            .ap(functions)
            .joinToString()
    println(result) //2, 4, 6, 4, 5, 6
}*/

/*fun main(args: Array<String>) {
    val curriedSum: (Int) -> (Int) -> Int = { i -> { j -> i + j } }
    println(Option.pure(5).ap(Option.pure(4).map(curriedSum)))  // Some(value=9)
}*/

/*fun main(args: Array<String>) {
    val curriedSum: (Int) -> (Int) -> Int = { i -> { j -> i + j } }
    println((Option.pure(curriedSum) `(*)` Option.pure(4)) `(*)` Option.pure(5))
}*/


/*fun main(args: Array<String>) {
    val curried: (Int) -> (Int) -> Int = { i -> { j -> i + j } }

    Option.pure(5).flatMap {a ->
        Option.pure(4).flatMap { b ->
            Option.pure(curried(a)(b))
        }
    }
}*/


/*
fun main(args: Array<String>) {
    val half = { i: Int ->
        if (i % 2 == 0) {
            Option.pure(i / 2)
        } else {
            Option.None
        }
    }
    
    println(Option.pure(20)
            .flatMap(half)
            .flatMap(half)
            .flatMap(half))
}*/


/*
fun main(args: Array<String>) {
    listOf(1, 2, 3)
            .map { i -> i * 2 }
            .map(Int::toString)
            .forEach(::println)
}*/

/*
fun main(args: Array<String>) {
    val add3AndMultiplyBy2: (Int) -> Pair<Int, Int> = { i:Int -> i + 3 }.ap { original -> { j:Int -> original to j * 2 } }
    println(add3AndMultiplyBy2(0)) //(0, 6)
    println(add3AndMultiplyBy2(1)) //(1, 8)
    println(add3AndMultiplyBy2(2)) //(2, 10)
}*/

fun main(args: Array<String>) {
    val add3AndMultiplyBy2: (Int) -> Int = { i: Int -> i + 3 }.ap { { j: Int -> j * 2 } }
    println(add3AndMultiplyBy2(0)) //6
    println(add3AndMultiplyBy2(1)) //8
    println(add3AndMultiplyBy2(2)) //10
}

/*
fun main(args: Array<String>) {
    val f: (String) -> Int = Function1.pure(0)
    println(f("Hello,"))    //0
    println(f("World"))     //0
    println(f("!"))         //0
}*/

