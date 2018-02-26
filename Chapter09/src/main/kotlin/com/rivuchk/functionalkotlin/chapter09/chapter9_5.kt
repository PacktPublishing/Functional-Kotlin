package com.rivuchk.functionalkotlin.chapter09

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

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

    val list = listOf("Str 1","Str 2","Str 3","Str 4")
    val observableFromIterable: Observable<String> = Observable.fromIterable(list)//1
    observableFromIterable.subscribe(observer)


    val callable = object : Callable<String> {
        override fun call(): String {
            return "I'm From Callable"
        }

    }
    val observableFromCallable:Observable<String> = Observable.fromCallable(callable)//2
    observableFromCallable.subscribe(observer)

    val future:Future<String> = object : Future<String> {
        val retStr = "I'm from Future"

        override fun get() = retStr

        override fun get(timeout: Long, unit: TimeUnit?)  = retStr

        override fun isDone(): Boolean = true

        override fun isCancelled(): Boolean = false

        override fun cancel(mayInterruptIfRunning: Boolean): Boolean = false

    }
    val observableFromFuture:Observable<String> = Observable.fromFuture(future)//3
    observableFromFuture.subscribe(observer)
}
