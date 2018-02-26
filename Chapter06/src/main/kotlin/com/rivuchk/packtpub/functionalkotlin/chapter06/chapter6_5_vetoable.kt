package com.rivuchk.packtpub.functionalkotlin.chapter06

import kotlin.properties.Delegates

var myIntEven:Int by Delegates.vetoable(0) {
    property, oldValue, newValue ->
    println("${property.name} $oldValue -> $newValue")
    newValue%2==0
}

fun main(args: Array<String>) {
    myIntEven = 6
    myIntEven = 3
    println("myIntEven:$myIntEven")
}