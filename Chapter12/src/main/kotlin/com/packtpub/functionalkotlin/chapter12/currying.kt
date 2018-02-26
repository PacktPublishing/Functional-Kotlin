package com.packtpub.functionalkotlin.chapter12

import arrow.syntax.function.curried
import arrow.syntax.function.invoke

/*
fun main(args: Array<String>) {

	val strong: (String, String, String) -> String = { body, id, style -> "<strong id=\"$id\" style=\"$style\">$body</strong>" }

	val curriedStrong: (style: String) -> (id: String) -> (body: String) -> String = strong.reverse().curried()

	val greenStrong: (id: String) -> (body: String) -> String = curriedStrong("color:green")

	val uncurriedGreenStrong: (id: String, body: String) -> String = greenStrong.uncurried()

	println(greenStrong("movie5")("Green Inferno"))

	println(uncurriedGreenStrong("movie6", "Green Hornet"))

	"Fried Green Tomatoes" pipe ("movie7" pipe greenStrong) pipe ::println
}*/

fun main(args: Array<String>) {
	val strong: (String, String, String) -> String = { body, id, style -> "<strong id=\"$id\" style=\"$style\">$body</strong>" }

	println(strong.curried()("Batman Begins")("trilogy1")("color:black")) // Curried

	println(strong("The Dark Knight")("trilogy2")("color:black")) // Fake curried, just partial application

	println(strong(p2 = "trilogy3")(p2 = "color:black")("The Dark Knight rises")) // partial application
	
}