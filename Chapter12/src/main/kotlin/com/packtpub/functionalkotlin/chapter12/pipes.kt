package com.packtpub.functionalkotlin.chapter12

import arrow.syntax.function.pipe
import arrow.syntax.function.pipe3
import arrow.syntax.function.reverse

/*fun main(args: Array<String>) {
    val strong: (String) -> String = { body -> "<strong>$body</strong>" }

	"From a pipe".pipe(strong).pipe(::println)
}*/



/*fun main(args: Array<String>) {
	splitter(filterBills(calculatePrice(Quote(20.0, "Foo", "Shoes", 20))))

	Quote(20.0, "Foo", "Shoes", 20) pipe ::calculatePrice pipe ::filterBills pipe ::splitter
}*/

fun main(args: Array<String>) {
	val strong: (String, String, String) -> String = { body, id, style -> "<strong id=\"$id\" style=\"$style\">$body</strong>" }

	val redStrong: (String, String) -> String = "color: red" pipe3 strong.reverse()
	
	redStrong("movie3", "Three colors: Red") pipe ::println
}

