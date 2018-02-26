package com.packtpub.functionalkotlin.chapter01

/*
fun main(args: Array<String>) {
    val expression = object {
        val property = ""

        fun method(): Int {
            println("from an object expressions")
            return 42
        }
    }

    val i = "${expression.method()} ${expression.property}"
    println(i)
}*/

/*
class Outer {
    val internal = object {
        val property = ""
    }
}

fun main(args: Array<String>) {
    val outer = Outer()

    println(outer.internal.property) // Compilation error: Unresolved reference: property
}*/

/*
object Oven {
  fun process(product: Bakeable) {
    println(product.bake())
  }
}

fun main(args: Array<String>) {
    val myAlmondCupcake = Cupcake("Almond")
    Oven.process(myAlmondCupcake)
}*/

/*interface Oven {
  fun process(product: Bakeable)
}

object ElectricOven: Oven {
  override fun process(product: Bakeable) {
    println(product.bake())
  }
}

fun main(args: Array<String>) {
    val myAlmondCupcake = Cupcake("Almond")
    ElectricOven.process(myAlmondCupcake)
}*/

/*class Cupcake(flavour: String) : BakeryGood(flavour), Bakeable {
    override fun name(): String {
        return "cupcake"
    }

    companion object {
        fun almond(): Cupcake {
            return Cupcake("almond")
        }

        fun cheese(): Cupcake {
            return Cupcake("cheese")
        }
    }
}*/

class Cupcake(flavour: String) : BakeryGood(flavour), Bakeable {
    override fun name(): String {
        return "cupcake"
    }

    companion object Factory {
        fun almond(): Cupcake {
            return Cupcake("almond")
        }

        fun cheese(): Cupcake {
            return Cupcake("cheese")
        }
    }
}

/*
fun main(args: Array<String>) {
    val myBlueberryCupcake: BakeryGood = Cupcake("Blueberry")
    val myAlmondCupcake = Cupcake.almond()
    val myCheeseCupcake = Cupcake.cheese()
    val myCaramelCupcake = Cupcake("Caramel")
}*/

/*
fun main(args: Array<String>) {
    val myAlmondCupcake = Cupcake.almond()
    val myCheeseCupcake = myAlmondCupcake.cheese() //Compilation error: Unresolved reference: cheese
}*/

/*
fun main(args: Array<String>) {
    val factory: Cupcake.Companion = Cupcake.Companion
}*/

/*
fun main(args: Array<String>) {
    val factory: Cupcake.Factory = Cupcake.Factory
}*/

fun main(args: Array<String>) {
    val factory: Cupcake.Factory = Cupcake
}