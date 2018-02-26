package com.packtpub.functionalkotlin.chapter05

/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 4/12/17
 * Time: 12:03 AM
 */


enum class WolfActions {
	SLEEP, WALK, BITE
}

enum class WolfRelationships {
	FRIEND, SIBLING, ENEMY, PARTNER
}

operator fun Wolf.set(relationship: WolfRelationships, wolf: Wolf): Int {
	println("${wolf.name} is my new $relationship")
	return 1
}

operator fun Wolf.not() = "$name is angry!!!"


class Wolf(val name: String) {
	operator fun plus(wolf: Wolf) = Pack(mapOf(name to this, wolf.name to wolf))

	operator fun invoke(action: WolfActions) = when (action) {
		WolfActions.SLEEP -> "$name is sleeping"
		WolfActions.WALK -> "$name is walking"
		WolfActions.BITE -> "$name is biting"
	}


}

class Pack(val members: Map<String, Wolf>)

operator fun Pack.get(name:String) = members[name]!!


operator fun Pack.plus(wolf: Wolf) = Pack(this.members.toMutableMap() + (wolf.name to wolf))

fun main(args: Array<String>) {
	val talbot = Wolf("Talbot")

	talbot(WolfActions.SLEEP)

	val northPack: Pack = talbot + Wolf("Big Bertha") // talbot.plus(Wolf("..."))

	val biggerPack = northPack + Wolf("Bad Wolf")

	val badWolf = biggerPack["Bad Wolf"]

	talbot[WolfRelationships.ENEMY] = badWolf

	!talbot

	
}