package com.packtpub.functionalkotlin.chapter12

import arrow.optics.Lens
import arrow.optics.optics

typealias GB = Int

@optics data class Memory(val size: GB) {
	companion object
}
@optics data class MotherBoard(val brand: String, val memory: Memory) {
	companion object
}
@optics data class Laptop(val price: Double, val motherBoard: MotherBoard) {
	companion object
}


/*
private fun main() {
	val laptopX8 = Laptop(500.0, MotherBoard("X", Memory(8)))

	val laptopX16 = laptopX8.copy(
			price = 780.0,
			motherBoard = laptopX8.motherBoard.copy(
					memory = laptopX8.motherBoard.memory.copy(
							size = laptopX8.motherBoard.memory.size * 2
					)
			)
	)

	println("laptopX16 = $laptopX16")
}*/


val laptopPrice: Lens<Laptop, Double> = Lens(
		get = { laptop -> laptop.price },
		set = { laptop, price -> laptop.copy(price = price) }
)

/*val laptopPrice: Lens<Laptop, Double> = Lens(
		get = { laptop -> laptop.price },
		set = { laptop: Laptop, price: Double -> laptop.copy(price = price) }
)

val laptopMotherBoard: Lens<Laptop, MotherBoard> = Lens(
		get = { laptop -> laptop.motherBoard },
		set = { laptop, mb -> laptop.copy(motherBoard = mb) }
)

val motherBoardMemory: Lens<MotherBoard, Memory> = Lens(
		get = { mb -> mb.memory },
		set = { memory -> { mb -> mb.copy(memory = memory) } }
)

val memorySize: Lens<Memory, GB> = Lens(
		get = { memory -> memory.size },
		set = { memory, size -> memory.copy(size = size) }
)*/

/*
private fun main() {
	val laptopX8 = Laptop(500.0, MotherBoard("X", Memory(8)))

	val laptopMemorySize = laptopMotherBoard compose motherBoardMemory compose memorySize

	val laptopX16 = laptopMemorySize.modify(laptopPrice.set(laptopX8, 780.0)) { size ->
		size * 2
	}

	println("laptopX16 = $laptopX16")
}*/

private fun main() {
	val laptopX8 = Laptop(500.0, MotherBoard("X", Memory(8)))

	val laptopMemorySize: Lens<Laptop, GB> = Laptop.motherBoard compose MotherBoard.memory compose Memory.size

	val laptopX16 = laptopMemorySize.modify(Laptop.price.set(laptopX8, 780.0)) { size ->
		size * 2
	}

	println("laptopX16 = $laptopX16")
}