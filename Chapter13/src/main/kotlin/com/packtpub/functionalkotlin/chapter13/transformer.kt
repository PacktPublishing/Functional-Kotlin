package com.packtpub.functionalkotlin.chapter13

import arrow.core.*
import arrow.syntax.function.pipe
import arrow.typeclasses.binding
import java.util.*
import kotlin.math.absoluteValue


fun Random.change(i: Int): Boolean = nextInt(101) < i

object CustomerService {
    //
    private val random = Random()

    fun findAge(user: String): Either<String, Option<Int>> {
        return if (random.change(80)) {
            Right(run {
                if (random.change(80)) {
                    Some(random.nextInt(100))
                } else {
                    None
                }

            })
        } else {
            Left("DB error searching for $user's age")
        }

    }
}

/*fun main(args: Array<String>) {
	val anakinAge = UserService.findAge("Anakin")

	anakinAge.fold(::identity, { op ->
			op.fold({ "Not found" }, Int::toString)
		}) pipe ::println
}*/

/*fun main(args: Array<String>) {
	val anakinAge = UserService.findAge("Anakin")
	val padmeAge = UserService.findAge("Padme")

	val difference: Either<String, Option<Either<String, Option<Int>>>> = anakinAge.map { aOp ->
		aOp.map { a ->
			padmeAge.map { pOp ->
				pOp.map { p ->
					(a - p).absoluteValue
				}
			}
		}
	}

	difference.fold(::identity, { op1 ->
		op1.fold({ "Not Found" }, { either ->
			either.fold(::identity, { op2 ->
				op2.fold({ "Not Found" }, Int::toString) })
		})
	}) pipe ::println
}*/


fun main(args: Array<String>) {
    val anakinAge = CustomerService.findAge("Anakin")
    val padmeAge = CustomerService.findAge("Padme")

    val difference: Either<String, Option<Option<Int>>> = Either.monad<String>().binding {
        val aOp = anakinAge.bind()
        val pOp = padmeAge.bind()
        aOp.map { a ->
            pOp.map { p ->
                (a - p).absoluteValue
            }
        }
    }.ev()

    difference.fold(::identity, { op1 ->
        op1.fold({ "Not found" }, { op2 ->
            op2.fold({ "Not found" }, Int::toString)
        })
    }) pipe ::println
}


/*fun main(args: Array<String>) {
	val anakinAge = CustomerService.findAge("Anakin")
	val padmeAge = CustomerService.findAge("Padme")

	val difference: Either<String, Option<Int>> = Either.monad<String>().binding {
		val aOp: Option<Int> = anakinAge.bind()
		val pOp: Option<Int> = padmeAge.bind()
		Option.monad().binding {
			val a: Int = aOp.bind()
			val p: Int = pOp.bind()
			(a - p).absoluteValue
		}.ev()
	}.ev()

	difference.fold(::identity, { op ->
		op.fold({ "Not found" }, Int::toString)
	}) pipe ::println
}*/

/*fun main(args: Array<String>) {
    val anakinAge: Either<String, Option<Int>> = CustomerService.findAge("Anakin")
    val padmeAge: Either<String, Option<Int>> = CustomerService.findAge("Padme")

    val difference: Either<String, Option<Int>> = OptionT.monad<EitherKindPartial<String>>().binding {
        val a = OptionT(anakinAge).bind()
        val p = OptionT(padmeAge).bind()
        (a - p).absoluteValue
    }.value().ev()


    difference.fold(::identity, { op ->
        op.fold({ "Not found" }, Int::toString)
    }) pipe ::println
}*/



