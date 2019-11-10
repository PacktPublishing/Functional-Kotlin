package com.packtpub.functionalkotlin.chapter12

import arrow.core.constant
import arrow.core.identity

private fun main() {

	val oneToFour = 1..4

	println("With identity: ${oneToFour.map(::identity).joinToString()}") //1, 2, 3, 4

	println("With constant: ${oneToFour.map(constant(1)).joinToString()}") //1, 1, 1, 1
	
}