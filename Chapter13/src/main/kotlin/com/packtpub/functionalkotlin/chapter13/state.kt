package com.packtpub.functionalkotlin.chapter13

import arrow.core.None
import arrow.core.Option
import arrow.core.Tuple2
import arrow.core.toT
import arrow.data.State
import arrow.data.ev
import arrow.data.run
import arrow.data.runA
import arrow.instances.monad
import arrow.syntax.option.some
import arrow.syntax.tuples.plus
import arrow.typeclasses.binding

typealias PriceLog = MutableList<Tuple2<String, Double>>

fun addVat(): State<PriceLog, Unit> = State { log: PriceLog ->
    val (_, price) = log.last()
    val vat = price * 0.2
    log.add("Add VAT: $vat" toT price + vat)
    log toT Unit
}

fun applyDiscount(threshold: Double, discount: Double): State<PriceLog, Unit> = State { log ->
    val (_, price) = log.last()
    if (price > threshold) {
        log.add("Applying -$discount" toT price - discount)
    } else {
        log.add("No discount applied" toT price)
    }
    log toT Unit
}

fun finalPrice(): State<PriceLog, Double> = State { log ->
    val (_, price) = log.last()
    log.add("Final Price" toT price)
    log toT price
}


fun calculatePrice(threshold: Double, discount: Double) = State().monad<PriceLog>().binding {
    addVat().bind() //Unit
    applyDiscount(threshold, discount).bind() //Unit
    val price: Double = finalPrice().bind()
    price
}.ev()


fun main(args: Array<String>) {
    val (history: PriceLog, price: Double) = calculatePrice(100.0, 2.0).run(mutableListOf("Init" toT 15.0))
    println("Price: $price")
    println("::History::")
    history
            .map { (text, value) -> "$text\t|\t$value" }
            .forEach(::println)

    val bigPrice: Double = calculatePrice(100.0, 2.0).runA(mutableListOf("Init" toT 1000.0))
    println("bigPrice = $bigPrice")
}


fun <T, S> unfold(s: S, state: State<S, Option<T>>): Sequence<T> {
    val (actualState: S, value: Option<T>) = state.run(s)
    return value.fold(
            { sequenceOf() },
            { t ->
                sequenceOf(t) + unfold(actualState, state)
            })
}

fun <T> elements(element: T, size: Int): Sequence<T> {
    return unfold(1, State { i ->
        if (size > i) {
            (i + 1) toT element.some()
        } else {
            0 toT None
        }
    })
}

fun factorial(size: Int): Sequence<Long> {
    return sequenceOf(1L) + unfold(1L toT 1, State { (acc, n) ->
        if (size > n) {
            val x = n * acc
            (x toT n + 1) toT x.some()
        } else {
            (0L toT 0) toT None
        }
    })
}

fun fib(size: Int): Sequence<Long> {
    return sequenceOf(1L) + unfold((0L toT 1L) + 1, State { (cur, next, n) ->
        if (size > n) {
            val x = cur + next
            ((next toT x) + (n + 1)) toT x.some()
        } else {
            ((0L toT 0L) + 0) toT None
        }
    })
}


/*
fun main(args: Array<String>) {
    factorial(10).forEach(::println)
    fib(10).forEach(::println)
}*/
