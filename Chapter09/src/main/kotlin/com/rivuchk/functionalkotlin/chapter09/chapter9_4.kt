package com.rivuchk.functionalkotlin.chapter09

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun main(args: Array<String>) {

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("All Completed")
        }

        override fun onNext(item: String) {
            println("Next $item")
        }

        override fun onError(e: Throwable) {
            println("Error Occured => ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("New Subscription ")
        }
    }//Create Observer

    val observable:Observable<String> = Observable.create<String> {//1
        it.onNext("Emitted 1")
        it.onNext("Emitted 2")
        it.onNext("Emitted 3")
        it.onNext("Emitted 4")
        it.onComplete()
    }

    observable.subscribe(observer)

    val observable2:Observable<String> = Observable.create<String> {//2
        it.onNext("Emitted 1")
        it.onNext("Emitted 2")
        it.onError(Exception("My Exception"))
    }

    observable2.subscribe(observer)
}
