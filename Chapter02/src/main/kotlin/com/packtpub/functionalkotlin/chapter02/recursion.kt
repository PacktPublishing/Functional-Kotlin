package com.packtpub.functionalkotlin.chapter02

/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 9/02/18
 * Time: 5:40 AM
 */
fun factorial(n: Long): Long {
    var result = 1L
    for (i in 1..n) {
        result *= i
    }
    return result
}

fun functionalFactorial(n: Long): Long {
    fun go(n: Long, acc: Long): Long {
        return if (n <= 0) {
            acc
        } else {
            go(n - 1, n * acc)
        }
    }

    return go(n, 1)
}

fun tailrecFactorial(n: Long): Long {
    tailrec fun go(n: Long, acc: Long): Long {
        return if (n <= 0) {
            acc
        } else {
            go(n - 1, n * acc)
        }
    }

    return go(n, 1)
}

fun fib(n: Long): Long {
    return when (n) {
        0L -> 0
        1L -> 1
        else -> {
            var a = 0L
            var b = 1L
            var c = 0L
            for (i in 2..n) {
                c = a + b
                a = b
                b = c
            }
            c
        }
    }
}

fun functionalFib(n: Long): Long {
    fun go(n: Long, prev: Long, cur: Long): Long {
        return if (n == 0L) {
            prev
        } else {
            go(n - 1, cur, prev + cur)
        }
    }

    return go(n, 0, 1)
}

fun tailrecFib(n: Long): Long {
    tailrec fun go(n: Long, prev: Long, cur: Long): Long {
        return if (n == 0L) {
            prev
        } else {
            go(n - 1, cur, prev + cur)
        }
    }

    return go(n, 0, 1)
}

fun executionTime(body: () -> Unit): Long {
    val startTime = System.nanoTime()
    body()
    val endTime = System.nanoTime()
    return endTime - startTime
}

/*
fun main(args: Array<String>) {
    println("factorial :" + executionTime { factorial(20) })
    println("functionalFactorial :" + executionTime { functionalFactorial(20) })
    println("tailrecFactorial :" + executionTime { tailrecFactorial(20) })
}*/

fun main(args: Array<String>) {
    println("fib :" + executionTime { fib(93) })
    println("functionalFib :" + executionTime { functionalFib(93) })
    println("tailrecFib :" + executionTime { tailrecFib(93) })
}