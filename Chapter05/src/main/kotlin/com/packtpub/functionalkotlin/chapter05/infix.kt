package com.packtpub.functionalkotlin.chapter05

/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 1/12/17
 * Time: 3:19 AM
 */

infix fun Int.superOperation(i: Int) = this + i


object All {
	infix fun your(base: Pair<Base, Us>) {}
}

object Base {
	infix fun are(belong: Belong) = this
}

object Belong

object Us

infix fun String.`(╯°□°）╯︵ ┻━┻`(s: String) = "*$this flips table at $s*"

fun main(args: Array<String>) {

	/*1 superOperation 2
	1.superOperation(2)

	"Adam" `(╯°□°）╯︵ ┻━┻` "Ben"*/
	All your (Base are Belong to Us)
}
