package com.packtpub.functionalkotlin.chapter01

/*interface Oven {
    fun process(product: Bakeable)
}*/

interface Machine<T> {
    fun process(product: T)
}

//interface Oven: Machine<Bakeable>

