package com.rivuchk.packtpub.functionalkotlin.chapter06

import kotlin.properties.Delegates

var myCounter:Int by Delegates.vetoable(0) {
    property, oldValue, newValue ->
    println("${property.name} $oldValue -> $newValue")
    newValue>oldValue
}

fun main(args: Array<String>) {
    myCounter = 2
    println("myCounter:$myCounter")
    myCounter = 5
    myCounter = 4
    println("myCounter:$myCounter")
    myCounter++
    myCounter--
    println("myCounter:$myCounter")
}