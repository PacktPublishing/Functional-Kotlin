package com.rivuchk.functionalkotlin.chapter09

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable

fun main(args: Array<String>) {
    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Completed")
        }

        override fun onNext(item: String) {
            println("Received-> $item")
        }

        override fun onError(e: Throwable) {
            println("Error Occured => ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Subscription")
        }
    }//Create Observer
    val list:List<String> = listOf("Str 1","Str 2","Str 3","Str 4")

    val observable: Observable<String> = list.toObservable()

    observable.subscribe(observer)

}