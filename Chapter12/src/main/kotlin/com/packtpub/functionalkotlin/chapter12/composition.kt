package com.packtpub.functionalkotlin.chapter12

import arrow.syntax.function.andThen
import arrow.syntax.function.forwardCompose
import java.util.*


data class Quote(val value: Double, val client: String, val item: String, val quantity: Int)

data class Bill(val value: Double, val client: String)

data class PickingOrder(val item: String, val quantity: Int)

fun calculatePrice(quote: Quote) = Bill(quote.value * quote.quantity, quote.client) to PickingOrder(quote.item, quote.quantity)

fun filterBills(billAndOrder: Pair<Bill, PickingOrder>): Pair<Bill, PickingOrder>? {
	val (bill, _) = billAndOrder
	return if (bill.value >= 100) {
		billAndOrder
	} else {
		null
	}
}

fun warehouse(order: PickingOrder) {
	println("Processing order = $order")
}

fun accounting(bill: Bill) {
	println("processing = $bill")
}

fun splitter(billAndOrder: Pair<Bill, PickingOrder>?) {
	if (billAndOrder != null) {
		warehouse(billAndOrder.second)
		accounting(billAndOrder.first)
	}
}

fun main(args: Array<String>) {
	val salesSystem: (quote: Quote) -> Unit = ::calculatePrice andThen ::filterBills forwardCompose ::splitter
	salesSystem(Quote(20.0, "Foo", "Shoes", 1))
	salesSystem(Quote(20.0, "Bar", "Shoes", 200))
	salesSystem(Quote(2000.0, "Foo", "Motorbike", 1))
}

val strong: (String) -> String = { body -> "<strong>$body</strong>" }

val p: (String) -> String = { body -> "<p>$body</p>" }

val span: (String) -> String = { body -> "<span>$body</span>" }

val div: (String) -> String = { body -> "<div>$body</div>" }

val randomNames: () -> String = {
	if (Random().nextInt() % 2 == 0) {
		"foo"
	} else {
		"bar"
	}
}

/*
fun main(args: Array<String>) {
	val divStrong: (String) -> String = div compose strong

	val spanP: (String) -> String = p forwardCompose span

	val randomStrong: () -> String = randomNames andThen strong

	println(divStrong("Hello composition world!"))
	println(spanP("Hello composition world!"))
	println(randomStrong())
}*/
