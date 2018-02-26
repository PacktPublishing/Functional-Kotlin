package com.packtpub.functionalkotlin.chapter01

/*
fun main(args: Array<String>) {
    val anyGood = BakeryGood("Generic flavour")
}*/

/*
abstract class BakeryGood(val flavour: String) {
    fun eat(): String {
        return "nom, nom, nom... delicious $flavour ${name()}"
    }

    open fun name(): String {
        return "bakery good"
    }
}*/

/*abstract class BakeryGood(val flavour: String) {
    fun eat(): String {
        return "nom, nom, nom... delicious $flavour ${name()}"
    }

    abstract fun name(): String
}*/


abstract class BakeryGood(val flavour: String) {

    init {
        println("Preparing a new bakery good")
    }

    fun eat(): String {
        return "nom, nom, nom... delicious $flavour ${name()}"
    }

    abstract fun name(): String
}

/*abstract class BakeryGood(val flavour: String) {
  fun eat(): String {
    return "nom, nom, nom... delicious $flavour ${name()}"
  }

  fun bake(): String {
    return "is hot here, isn't??"
  }

  abstract fun name(): String
}*/

/*abstract class Bakeable {
    fun bake(): String {
        return "is hot here, isn't"
    }
}*/

interface Bakeable {
    fun bake(): String {
        return "is hot here, isn't"
    }
}

class Customer(val name: String) {
    fun eats(food: BakeryGood) {
        println("$name is eating... ${food.eat()}")
    }
}

/*class Cupcake(flavour: String) : BakeryGood(flavour), Bakeable() {
    override fun name(): String {
        return "cupcake"
    }
}*/

/*class Cupcake(flavour: String) : BakeryGood(flavour), Bakeable {
    override fun name(): String {
        return "cupcake"
    }
}*/

/*
fun main(args: Array<String>) {
    val myDonut = Donut("Custard", "Powdered sugar")
    val mario = Customer("Mario")
    mario.eats(myDonut)
}*/

fun main(args: Array<String>) {
    val mario = Customer("Mario")

    val food: BakeryGood = object : BakeryGood("TEST_1") {
        override fun name(): String {
            return "TEST_2"
        }
    }

    mario.eats(food)
}

open class Donut(flavour: String, val topping: String) : BakeryGood(flavour), Fried {

    override fun fry(): String {
        return "*swimming on oil*"
      }

    override fun name(): String {
        return "donut with $topping topping"
    }
}