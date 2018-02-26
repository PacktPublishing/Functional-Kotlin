package com.packtpub.functionalkotlin.chapter01

//class VeryBasic

/*fun main(args: Array<String>) {
    val basic: VeryBasic = VeryBasic()
}*/


/*
fun main(args: Array<String>) {
    val basic = VeryBasic()
}*/


/*
class BlueberryCupcake {
    var flavour = "Blueberry"
}

fun main(args: Array<String>) {
    val myCupcake = BlueberryCupcake()
    myCupcake.flavour = "Almond"
    println("My cupcake has ${myCupcake.flavour}")
}*/

/*
class BlueberryCupcake {
    val flavour = "Blueberry"
}

fun main(args: Array<String>) {
    val myCupcake = BlueberryCupcake()
    myCupcake.flavour = "Almond" //Compilation error: Val cannot be reassigned
    println("My cupcake has ${myCupcake.flavour}")
}*/

/*
class AlmondCupcake {
    val flavour = "Almond"
}

fun main(args: Array<String>) {
    val mySecondCupcake = AlmondCupcake()
    println("My second cupcake has ${mySecondCupcake.flavour} flavour")
}*/

/*class Cupcake(flavour: String) {
    val flavour = flavour
}*/

/*
class Cupcake(val flavour: String)

fun main(args: Array<String>) {
    val myBlueberryCupcake = Cupcake("Blueberry")
    val myAlmondCupcake = Cupcake("Almond")
    val myCheeseCupcake = Cupcake("Cheese")
    val myCaramelCupcake = Cupcake("Caramel")
}*/

/*
class Cupcake(val flavour: String) {
    fun eat(): String {
        return "nom, nom, nom... delicious $flavour cupcake"
    }
}

fun main(args: Array<String>) {
    val myBlueberryCupcake = Cupcake("Blueberry")
    println(myBlueberryCupcake.eat())
}*/
