package com.rivuchk.functionalkotlin.chapter09

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable

fun main(args: Array<String>) {

    val observer = object :Observer<Any>{//1
    override fun onComplete() {//2
        println("All Completed")
    }

        override fun onNext(item: Any) {//3
            println("Next $item")
        }

        override fun onError(e: Throwable) {//4
            println("Error Occured $e")
        }

        override fun onSubscribe(d: Disposable) {//5
            println("Subscribed to $d")
        }
    }

    val observable = listOf(1, "Two", 3, "Four", "Five", 5.5f).toObservable() //6

    observable.subscribe(observer)//7

    val observableOnList = Observable.just(listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f),
            listOf("List with 1 Item"),
            listOf(1,2,3))//8



    observableOnList.subscribe(observer)//9


}
