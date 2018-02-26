package com.packtpub.functionalkotlin.chapter01

/*
fun main(args: Array<String>) {

    val myAlmondCupcake = Cupcake.almond()

    val anyMachine = object : Machine<Any> {
      override fun process(product: Any) {
        println(product.toString())
      }
    }

    anyMachine.process(3)

    anyMachine.process("")

    anyMachine.process(myAlmondCupcake)
}*/

/*
fun main(args: Array<String>) {

    val anyMachine = object : Machine<Any> {
      override fun process(product: Any) {
        println(product.toString())
      }
    }

    val nullableCupcake: Cupcake? = Cupcake.almond()

    anyMachine.process(nullableCupcake) //Error:(32, 24) Kotlin: Type mismatch: inferred type is Cupcake? but Any was expected
}*/

/*
fun main(args: Array<String>) {
    val nullableCupcake: Cupcake? = Cupcake.almond()

    val length = nullableCupcake?.eat()?.length ?: ""
}*/

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Tasty(val tasty: Boolean = true)

@Tasty(false)
object ElectricOven : Oven {
    override fun process(product: Bakeable) {
        println(product.bake())
    }
}

@Tasty
class CinnamonRoll : Roll("Cinnamon")

@Tasty
interface Fried {
    fun fry(): String
}

fun main(args: Array<String>) {
    val annotations: List<Annotation> = ElectricOven::class.annotations

    for (annotation in annotations) {
        when (annotation) {
            is Tasty -> println("Is it tasty? ${annotation.tasty}")
            else -> println(annotation)
        }
    }
}