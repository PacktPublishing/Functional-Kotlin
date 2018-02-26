package com.packtpub.functionalkotlin.chapter01


/*
class Biscuit(val flavour: String) {
  fun eat(): String {
    return "nom, nom, nom... delicious $flavour biscuit"
  }
}*/

/*open class BakeryGood(val flavour: String) {
  fun eat(): String {
    return "nom, nom, nom... delicious $flavour bakery good"
  }
}

class Cupcake(flavour: String): BakeryGood(flavour)
class Biscuit(flavour: String): BakeryGood(flavour)*/

/*open class BakeryGood(val flavour: String) {
    fun eat(): String {
        return "nom, nom, nom... delicious $flavour ${name()}"
    }

    open fun name(): String {
        return "bakery good"
    }
}*/

/*class Cupcake(flavour: String) : BakeryGood(flavour) {
    override fun name(): String {
        return "cupcake"
    }
}*/

class Biscuit(flavour: String) : BakeryGood(flavour) {
    override fun name(): String {
        return "biscuit"
    }
}


/*
fun main(args: Array<String>) {
    val myBlueberryCupcake: BakeryGood = Cupcake("Blueberry")
    println(myBlueberryCupcake.eat())
}*/

open class Roll(flavour: String) : BakeryGood(flavour) {
    override fun name(): String {
        return "roll"
    }
}



/*open class Donut(flavour: String, val topping: String) : BakeryGood(flavour) {
    override fun name(): String {
        return "donut with $topping topping"
    }
}*/

fun main(args: Array<String>) {
    val myDonut = Donut("Custard", "Powdered sugar")
    println(myDonut.eat())
}