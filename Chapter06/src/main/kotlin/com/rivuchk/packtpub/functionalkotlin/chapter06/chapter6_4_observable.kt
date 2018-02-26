package com.rivuchk.packtpub.functionalkotlin.chapter06

import kotlin.properties.Delegates

var myStr:String by Delegates.observable("<Initial Value>") {
    property, oldValue, newValue ->
    println("Property `${property.name}` changed value from \"$oldValue\" to \"$newValue\"")
}

fun main(args: Array<String>) {
    myStr = "Change Value"
    myStr = "Change Value again"
}