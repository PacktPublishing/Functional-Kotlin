package com.rivuchk.packtpub.functionalkotlin.chapter06

import kotlin.properties.Delegates

var notNullStr:String by Delegates.notNull<String>()
var notInit:String by Delegates.notNull<String>()

fun main(args: Array<String>) {
    notNullStr = "Initial value"
    println(notNullStr)
    println(notInit)
}