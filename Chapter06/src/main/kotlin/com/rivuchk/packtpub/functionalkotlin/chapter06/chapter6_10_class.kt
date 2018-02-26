package com.rivuchk.packtpub.functionalkotlin.chapter06

interface Person {
    fun printName()
}

class PersonImpl(val name:String):Person {
    override fun printName() {
        println(name)
    }
}

class User(val person:Person):Person by person {
    override fun printName() {
        println("Printing Name:")
        person.printName()
    }
}

fun main(args: Array<String>) {
    val person = PersonImpl("Mario Arias")
    person.printName()
    println()
    val user = User(person)
    user.printName()
}