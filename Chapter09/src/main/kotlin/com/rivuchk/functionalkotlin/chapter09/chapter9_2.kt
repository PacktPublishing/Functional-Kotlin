package com.rivuchk.functionalkotlin.chapter09

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

fun main(args: Array<String>) {
    var list = listOf(1, "Two", 3, "Four", "Five", 5.5f) // 1
    var observable = list.toObservable();

    observable.subscribeBy(  // named arguments for lambda Subscribers
            onNext = { println(it) },
            onError =  { it.printStackTrace() },
            onComplete = { println("Done!") }
    )
}
