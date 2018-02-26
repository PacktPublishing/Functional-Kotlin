package com.packtpub.functionalkotlin.chapter05

/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 1/12/17
 * Time: 3:11 AM
 */


fun <T> time(body: () -> T): Pair<T, Long> {
	val startTime = System.nanoTime()
	val v = body()
	val endTime = System.nanoTime()
	return v to endTime - startTime
}

inline fun <T> inTime(body: () -> T): Pair<T, Long> {
	val startTime = System.currentTimeMillis()
	val v = body()
	val endTime = System.currentTimeMillis()
	return v to endTime - startTime
}

typealias UserListener = (User) -> Unit

data class User(val name: String)

class UserService {
	val listeners = mutableListOf<UserListener>()
	val users = mutableListOf<User>()

	inline fun transformName(crossinline transform: (name: String) -> String): List<User> {

		val buildUser = { name: String ->
			User(transform(name)) //compilation error: Can't inline transform here
		}

		return users.map { user -> buildUser(user.name) }
	}

	inline fun runListeners(builder: () -> User) {
		listeners.forEach { listener -> listener(builder()) }
	}

//	fun addListener(listener: (User) -> Unit) {
//		listeners += listener
//	}

//	inline fun addListener(listener: (User) -> Unit) {
//		listeners += listener
//	}
//
	inline fun addListener(noinline listener: (User) -> Unit) {
		listeners += listener
	}


}

fun main(args: Array<String>) {
//	val (_, time) = time { Thread.sleep(1000) }
	/*val (_, time) = time(object : Function0<Unit> {
		override fun invoke() {
			Thread.sleep(1000)
		}
	})*/
//	val (_, inTime) = inTime { Thread.sleep(1000) }

	/*val startTime = System.nanoTime()
	val v = Thread.sleep(1000)
	val endTime = System.nanoTime()
	val (_, inTime) = (v to endTime - startTime)*/

//	println("time = $time")
//	println("inTime = $inTime")

	UserService().transformName(String::toLowerCase)

}