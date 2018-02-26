package com.packtpub.functionalkotlin.chapter01

/*
fun main(args: Array<String>) {
    val myBlueberryCupcake: Cupcake = null //Compilation error: Null can not be a value of a non-null type Cupcake
}*/

/*
fun main(args: Array<String>) {
    val myBlueberryCupcake: Cupcake? = null
}*/

/*
fun eat(cupcake: Cupcake?){
//  something happens here
}

fun main(args: Array<String>) {
    val myAlmondCupcake = Cupcake.almond()

    eat(myAlmondCupcake)

    eat(null)
}*/

/*
fun main(args: Array<String>) {
    val cupcake: Cupcake = Cupcake.almond()
    val nullableCupcake: Cupcake? = Cupcake.almond()

    cupcake.eat() // Happy days
    nullableCupcake.eat() //Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type Cupcake?
}*/


fun main(args: Array<String>) {
    val nullableCupcake: Cupcake? = Cupcake.almond()

    if (nullableCupcake != null) {
      nullableCupcake.eat()
    }

    if (nullableCupcake is Cupcake) {
      nullableCupcake.eat()
    }

    when (nullableCupcake) {
      is Cupcake -> nullableCupcake.eat()
    }

    nullableCupcake?.eat()

    val result: String? = nullableCupcake?.eat()

    val length: Int? = nullableCupcake?.eat()?.length

    

}