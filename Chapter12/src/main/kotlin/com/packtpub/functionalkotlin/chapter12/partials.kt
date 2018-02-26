package com.packtpub.functionalkotlin.chapter12

import arrow.syntax.function.bind

fun partialSplitter(billAndOrder: Pair<Bill, PickingOrder>?, warehouse: (PickingOrder) -> Unit, accounting: (Bill) -> Unit) {
	if (billAndOrder != null) {
		warehouse(billAndOrder.second)
		accounting(billAndOrder.first)
	}
}

/*fun main(args: Array<String>) {
	val splitter: (billAndOrder: Pair<Bill, PickingOrder>?) -> Unit = ::partialSplitter.partially2 { order -> println("TESTING $order") }(p2 = ::accounting)

	val salesSystem: (quote: Quote) -> Unit = ::calculatePrice andThen ::filterBills forwardCompose splitter
	salesSystem(Quote(20.0, "Foo", "Shoes", 1))
	salesSystem(Quote(20.0, "Bar", "Shoes", 200))
	salesSystem(Quote(2000.0, "Foo", "Motorbike", 1))
}

fun main(args: Array<String>) {
	val strong: (String, String, String) -> String = { body, id, style -> "<strong id=\"$id\" style=\"$style\">$body</strong>" }

	val redStrong: (String, String) -> String = strong.partially3("font: red") //Explicit

	val blueStrong: (String, String) -> String = strong(p3 = "font: blue") //Implicit

	println(redStrong("Red Sonja", "movie1"))
	println(blueStrong("Deep Blue Sea", "movie2"))
}*/

fun main(args: Array<String>) {

	val footer:(String) -> String = {content -> "<footer>$content</footer>"}

	val fixFooter: () -> String = footer.bind("Functional Kotlin - 2018") //alias for partially1

	println(fixFooter())
}
