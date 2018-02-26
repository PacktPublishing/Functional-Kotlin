package com.packtpub.functionalkotlin.chapter12

import arrow.syntax.function.partially3
import arrow.syntax.function.reverse

fun main(args: Array<String>) {
	val strong: (String, String, String) -> String = { body, id, style -> "<strong id=\"$id\" style=\"$style\">$body</strong>" }

	val redStrong: (String, String) -> String = strong.partially3("font: red") //Explicit

	println(redStrong("Red Sonja", "movie1"))

	println(redStrong.reverse()("movie2", "The Hunt for Red October"))

}