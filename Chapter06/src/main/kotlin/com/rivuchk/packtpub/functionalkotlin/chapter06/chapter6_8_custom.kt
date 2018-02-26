package com.rivuchk.packtpub.functionalkotlin.chapter06

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class MakeEven(initialValue: Int):ReadWriteProperty<Any?,Int> {
    private var value:Int = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>) = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        val oldValue = value
        val wasEven = value%2==0
        if(wasEven) {
            this.value = value
        } else {
            this.value = value+1
        }
        afterAssignmentCall(property,oldValue,value,wasEven)
    }

    abstract fun afterAssignmentCall (property: KProperty<*>, oldValue: Int, newValue: Int, wasEven:Boolean):Unit
}

inline fun makeEven(initialValue: Int, crossinline onAssignment:(property: KProperty<*>, oldValue: Int, newValue: Int, wasEven:Boolean)->Unit):ReadWriteProperty<Any?, Int>
        =object : MakeEven(initialValue){
    override fun afterAssignmentCall(property: KProperty<*>, oldValue: Int, newValue: Int, wasEven: Boolean)
            = onAssignment(property,oldValue,newValue,wasEven)
}

var myEven:Int by makeEven(0) {
    property, oldValue, newValue, wasEven ->
    println("${property.name} $oldValue -> $newValue, Even:$wasEven")
}

fun main(args: Array<String>) {
    myEven = 6
    println("myEven:$myEven")
    myEven = 3
    println("myEven:$myEven")
    myEven = 5
    println("myEven:$myEven")
    myEven = 8
    println("myEven:$myEven")
}
