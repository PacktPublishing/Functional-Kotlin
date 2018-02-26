package com.packtpub.functionalkotlin.chapter02

val numbers: List<Int> = listOf(1, 2, 3, 4)

fun main(args: Array<String>) {
    val sum = numbers.reduce { acc, i ->
        println("acc, i = $acc, $i")
        acc + i
    }

    println(sum)
}

/*fun main(args: Array<String>) {
    val sum = numbers.fold(0) { acc, i ->
        println("acc, i = $acc, $i")
        acc + i
    }

    println(sum)
}*/

/*fun main(args: Array<String>) {
    val sum = numbers.fold(0) { acc, i -> acc + i }

    println(sum)
}*/

/*fun main(args: Array<String>) {
    val sum = numbers.sum()

    println(sum)
}*/

/*fun main(args: Array<String>) {
    var sum = 0

    for (i in numbers) {
       sum += i
    }

    println(sum)
}*/

/*fun main(args: Array<String>) {
    val numbersTwice: List<Int> = numbers.map { i -> i * 2 }
}*/

/*fun main(args: Array<String>) {
    val numbersTwice: MutableList<Int> = mutableListOf()

    for (i in numbers) {
        numbersTwice.add(i * 2) //Nice!
    }
}*/

/*fun main(args: Array<String>) {
    val numbersTwice: List<Int> = listOf()

    for (i in numbers) {
       numbersTwice.add(i * 2) //Compilation error: Unresolved reference: add
    }
}*/

/*
fun main(args: Array<String>) {
    for(i in numbers) {
       println("i = $i")
    }
}*/

/*
fun main(args: Array<String>) {
    numbers.forEach { i -> println("i = $i") }
}*/
