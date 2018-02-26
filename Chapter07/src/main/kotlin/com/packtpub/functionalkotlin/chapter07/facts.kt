package com.packtpub.functionalkotlin.chapter07


import com.github.salomonbrys.kotson.*
import com.google.gson.GsonBuilder
import com.packtpub.functionalkotlin.chapter07.ActorUserService.FactMsg.*
import com.packtpub.functionalkotlin.chapter07.ActorUserService.UserMsg.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.SendChannel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.task
import nl.komponents.kovenant.then
import org.h2.jdbcx.JdbcDataSource
import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import java.lang.Exception
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread


inline fun <T> inTime(body: () -> T): Pair<T, Long> {
	val startTime = System.currentTimeMillis()
	val v = body()
	val endTime = System.currentTimeMillis()
	return v to endTime - startTime
}

enum class Gender {
	MALE, FEMALE;

	companion object {
		fun valueOfIgnoreCase(name: String): Gender = valueOf(name.toUpperCase())
	}
}

//Model
typealias UserId = Int

data class User(val id: UserId, val firstName: String, val lastName: String, val gender: Gender)

data class Fact(val id: Int, val value: String, val user: User? = null)

//Services
interface UserService {
	fun getFact(id: UserId): Fact
}

class SynchronousUserService(private val userClient: UserClient,
							 private val factClient: FactClient,
							 private val userRepository: UserRepository,
							 private val factRepository: FactRepository) : UserService {

	override fun getFact(id: UserId): Fact {
		val user = userRepository.getUserById(id)
		return if (user == null) {
			val userFromService = userClient.getUser(id)
			userRepository.insertUser(userFromService)
			getFact(userFromService)
		} else {
			factRepository.getFactByUserId(id) ?: getFact(user)
		}
	}

	private fun getFact(user: User): Fact {
		val fact = factClient.getFact(user)
		factRepository.insertFact(fact)
		return fact
	}
}

// Clients
abstract class WebClient {
	protected val apacheClient = ApacheClient()

	protected val gson = GsonBuilder()
			.registerTypeAdapter<User> {
				deserialize { des ->
					val json = des.json
					User(json["info"]["seed"].int,
							json["results"][0]["name"]["first"].string.capitalize(),
							json["results"][0]["name"]["last"].string.capitalize(),
							Gender.valueOfIgnoreCase(json["results"][0]["gender"].string))

				}
			}
			.registerTypeAdapter<Fact> {
				deserialize { des ->
					val json = des.json
					Fact(json["value"]["id"].int,
							json["value"]["joke"].string)
				}
			}.create()!!
}


interface UserClient {
	fun getUser(id: UserId): User
}

interface SuspendUserClient {
	suspend fun getUser(id: UserId): User
}

class Http4KUserClient : WebClient(), UserClient {
	override fun getUser(id: UserId): User {
		return gson.fromJson(apacheClient(Request(Method.GET, "https://randomuser.me/api")
				.query("seed", id.toString()))
				.bodyString())
	}
}

interface FactClient {
	fun getFact(user: User): Fact
}


class Http4KFactClient : WebClient(), FactClient {
	override fun getFact(user: User): Fact {
		return gson.fromJson<Fact>(apacheClient(Request(Method.GET, "http://api.icndb.com/jokes/random")
				.query("firstName", user.firstName)
				.query("lastName", user.lastName))
				.bodyString())
				.copy(user = user)
	}
}

//Repositories

abstract class JdbcRepository(protected val template: JdbcTemplate) {
	protected fun <T> toNullable(block: () -> T): T? {
		return try {
			block()
		} catch (_: EmptyResultDataAccessException) {
			null
		}
	}
}

interface UserRepository {
	fun getUserById(id: UserId): User?
	fun insertUser(user: User)
}


class JdbcUserRepository(template: JdbcTemplate) : JdbcRepository(template), UserRepository {
	override fun getUserById(id: UserId): User? {
		return toNullable {
			template.queryForObject("select * from USERS where id = ?", id) { resultSet, _ ->
				with(resultSet) {
					User(getInt("ID"),
							getString("FIRST_NAME"),
							getString("LAST_NAME"),
							Gender.valueOfIgnoreCase(getString("GENDER")))
				}
			}
		}
	}

	override fun insertUser(user: User) {
		template.update("INSERT INTO USERS VALUES (?,?,?,?)",
				user.id,
				user.firstName,
				user.lastName,
				user.gender.name)
	}
}

interface FactRepository {
	fun getFactByUserId(id: UserId): Fact?
	fun insertFact(fact: Fact)
}


class JdbcFactRepository(template: JdbcTemplate) : JdbcRepository(template), FactRepository {
	override fun getFactByUserId(id: Int): Fact? {
		return toNullable {
			template.queryForObject("select * from USERS as U inner join FACTS as F on U.ID = F.USER where U.ID = ?", id) { resultSet, _ ->
				with(resultSet) {
					Fact(getInt(5),
							getString(6),
							User(getInt(1),
									getString(2),
									getString(3),
									Gender.valueOfIgnoreCase(getString(4))))
				}
			}
		}
	}

	override fun insertFact(fact: Fact) {
		template.update("INSERT INTO FACTS VALUES (?,?,?)", fact.id, fact.value, fact.user?.id)
	}
}

//Callback
class CallbackUserService(private val userClient: CallbackUserClient,
						  private val factClient: CallbackFactClient,
						  private val userRepository: CallbackUserRepository,
						  private val factRepository: CallbackFactRepository) : UserService {

	override fun getFact(id: UserId): Fact {
		var aux: Fact? = null
		userRepository.getUserById(id) { user ->
			if (user == null) {
				userClient.getUser(id) { userFromClient ->
					userRepository.insertUser(userFromClient) { }
					factClient.get(userFromClient) { fact ->
						factRepository.insertFact(fact) {}
						aux = fact
					}

				}
			} else {
				factRepository.getFactByUserId(id) { fact ->
					if (fact == null) {
						factClient.get(user) { factFromClient ->
							factRepository.insertFact(factFromClient) {}
							aux = factFromClient
						}
					} else {
						aux = fact
					}
				}
			}
		}
		while (aux == null) {
			Thread.sleep(2)
		}
		return aux!!
	}
}

class CallbackUserRepository(private val userRepository: UserRepository) {
	fun getUserById(id: UserId, callback: (User?) -> Unit) {
		thread {
			callback(userRepository.getUserById(id))
		}
	}

	fun insertUser(user: User, callback: () -> Unit) {
		thread {
			userRepository.insertUser(user)
			callback()
		}

	}
}

class CallbackFactRepository(private val factRepository: FactRepository) {
	fun getFactByUserId(id: Int, callback: (Fact?) -> Unit) {
		thread {
			callback(factRepository.getFactByUserId(id))
		}
	}

	fun insertFact(fact: Fact, callback: () -> Unit) {
		thread {
			factRepository.insertFact(fact)
			callback()
		}
	}
}

class CallbackUserClient(private val client: UserClient) {
	fun getUser(id: Int, callback: (User) -> Unit) {
		thread {
			callback(client.getUser(id))
		}
	}
}

class CallbackFactClient(private val client: FactClient) {
	fun get(user: User, callback: (Fact) -> Unit) {
		thread {
			callback(client.getFact(user))
		}
	}
}

// Future

class FutureUserService(private val userClient: UserClient,
						private val factClient: FactClient,
						private val userRepository: UserRepository,
						private val factRepository: FactRepository) : UserService {
	override fun getFact(id: UserId): Fact {

		val executor = Executors.newFixedThreadPool(2)

		val user = executor.submit<User?> { userRepository.getUserById(id) }.get()
		return if (user == null) {
			val userFromService = executor.submit<User> { userClient.getUser(id) }.get()
			executor.submit { userRepository.insertUser(userFromService) }
			getFact(userFromService, executor)
		} else {
			executor.submit<Fact> {
				factRepository.getFactByUserId(id) ?: getFact(user, executor)
			}.get()
		}.also {
			executor.shutdown()
		}
	}

	private fun getFact(user: User, executor: ExecutorService): Fact {
		val fact = executor.submit<Fact> { factClient.getFact(user) }.get()
		executor.submit { factRepository.insertFact(fact) }
		return fact
	}
}


class CoroutineUserService(private val userClient: UserClient,
						   private val factClient: FactClient,
						   private val userRepository: UserRepository,
						   private val factRepository: FactRepository) : UserService {
	override fun getFact(id: UserId): Fact = runBlocking {
		val user = async { userRepository.getUserById(id) }.await()
		if (user == null) {
			val userFromService = async { userClient.getUser(id) }.await()
			launch { userRepository.insertUser(userFromService) }
			getFact(userFromService)
		} else {
			async { factRepository.getFactByUserId(id) ?: getFact(user) }.await()
		}
	}

	private suspend fun getFact(user: User):Fact {
		val fact: Deferred<Fact> = async { factClient.getFact(user) }
		launch { factRepository.insertFact(fact.await()) }
		return fact.await()
	}
}


class PromiseUserService(private val userClient: UserClient,
						 private val factClient: FactClient,
						 private val userRepository: UserRepository,
						 private val factRepository: FactRepository) : UserService {

	override fun getFact(id: UserId): Fact {

		return (task {
			userRepository.getUserById(id)
		} then { user ->
			if (user == null) {
				task {
					userClient.getUser(id)
				} success { userFromService ->
					userRepository.insertUser(userFromService)
				} then { userFromService ->
					getFact(userFromService).get()
				}
			} else {
				task { factRepository.getFactByUserId(id) ?: getFact(user).get() }
			}
		}).get().get()
	}

	private fun getFact(user: User): Promise<Fact, Exception> = task {
		factClient.getFact(user)
	} success { fact ->
		factRepository.insertFact(fact)
	}
}

class ActorUserService(private val userClient: UserClient,
					   private val factClient: FactClient,
					   private val userRepository: UserRepository,
					   private val factRepository: FactRepository) : UserService {

	sealed class UserMsg {
		data class GetFact(val id: UserId, val returner: SendChannel<Fact>) : UserMsg()
		data class MaybeUser(val id: UserId, val user: User?, val returner: SendChannel<Fact>) : UserMsg()
		data class InsertUser(val user: User) : UserMsg()
	}


	sealed class FactMsg {
		data class UserFromDB(val user: User, val inserted: Boolean, val returner: SendChannel<Fact>) : FactMsg()
		data class InsertFact(val fact: Fact) : FactMsg()
		data class SomeFact(val fact: Fact, val returner: SendChannel<Fact>) : FactMsg()
		data class GetFactFromService(val user: User, val returner: SendChannel<Fact>) : FactMsg()
	}

	private fun factActor() = actor<FactMsg>(capacity = 64) {
		for (msg in channel) {
			when (msg) {
				is UserFromDB -> {
					if (msg.inserted) {
						channel.send(GetFactFromService(msg.user, msg.returner))
					} else {
						val fact = factRepository.getFactByUserId(msg.user.id)
						if (fact == null) {
							channel.send(GetFactFromService(msg.user, msg.returner))
						} else {
							channel.send(SomeFact(fact, msg.returner))
						}
					}
				}
				is GetFactFromService -> {
					val fact = factClient.getFact(msg.user)
					channel.send(InsertFact(fact))
					channel.send(SomeFact(fact, msg.returner))
				}
				is SomeFact -> msg.returner.send(msg.fact)
				is InsertFact -> factRepository.insertFact(msg.fact)
			}
		}
	}

	private fun userActor(factActor: SendChannel<FactMsg>) = actor<UserMsg>(capacity = 64) {
		for (msg in channel) {
			when (msg) {
				is GetFact -> channel.send(MaybeUser(msg.id, userRepository.getUserById(msg.id), msg.returner))
				is MaybeUser -> {
					val (user, inserted) = if (msg.user == null) {
						val user = userClient.getUser(msg.id)
						channel.send(InsertUser(user))
						user to true
					} else {
						msg.user to false
					}
					factActor.send(UserFromDB(user, inserted, msg.returner))
				}
				is InsertUser -> userRepository.insertUser(msg.user)
			}
		}
	}

	private val userActor = userActor(factActor())

	override fun getFact(id: UserId): Fact = runBlocking {

		val returner = Channel<Fact>()
		userActor.send(GetFact(id, returner))
		returner.receive()
	}
}


fun main(args: Array<String>) = awaitEnter {
	pauseUntilEnter()

	fun execute(userService: UserService, id: Int) {
		val (fact, time) = inTime {
			userService.getFact(id)
		}
		println("fact = $fact")
		println("time = $time ms.")
	}

	/*val template = initJdbcTemplate()

	val userClient = Http4KUserClient()
	val factClient = Http4KFactClient()
	val userRepository = JdbcUserRepository(template)
	val factRepository = JdbcFactRepository(template)*/

	val userClient = MockUserClient()
	val factClient = MockFactClient()
	val userRepository = MockUserRepository()
	val factRepository = MockFactRepository()

	/*val userService = SynchronousUserService(userClient,
			factClient,
			userRepository,
			factRepository)

	val userService = CallbackUserService(
			CallbackUserClient(userClient),
			CallbackFactClient(factClient),
			CallbackUserRepository(userRepository),
			CallbackFactRepository(factRepository))

	val userService = FutureUserService(userClient,
			factClient,
			userRepository,
			factRepository)

	val userService = PromiseUserService(userClient,
				factClient,
				userRepository,
				factRepository)

	val userService = CoroutineUserService(userClient,
			factClient,
			userRepository,
			factRepository)*/

	val userService = ActorUserService(userClient,
			factClient,
			userRepository,
			factRepository)


	execute(userService, 1)
	execute(userService, 2)
	execute(userService, 1)
	execute(userService, 2)
	execute(userService, 3)
	execute(userService, 4)
	execute(userService, 5)
	execute(userService, 10)
	execute(userService, 100)


}

class MockUserClient : UserClient {
	override fun getUser(id: UserId): User {
		println("MockUserClient.getUser")
		Thread.sleep(500)
		return User(id, "Foo", "Bar", Gender.FEMALE)
	}
}

class MockFactClient : FactClient {
	override fun getFact(user: User): Fact {
		println("MockFactClient.getFact")
		Thread.sleep(500)
		return Fact(Random().nextInt(), "FACT ${user.firstName}, ${user.lastName}", user)
	}
}

class MockUserRepository : UserRepository {
	private val users = hashMapOf<UserId, User>()

	override fun getUserById(id: UserId): User? {
		println("MockUserRepository.getUserById")
		Thread.sleep(200)
		return users[id]
	}

	override fun insertUser(user: User) {
		println("MockUserRepository.insertUser")
		Thread.sleep(200)
		users[user.id] = user
	}
}

class MockFactRepository : FactRepository {

	private val facts = hashMapOf<UserId, Fact>()

	override fun getFactByUserId(id: UserId): Fact? {
		println("MockFactRepository.getFactByUserId")
		Thread.sleep(200)
		return facts[id]
	}

	override fun insertFact(fact: Fact) {
		println("MockFactRepository.insertFact")
		Thread.sleep(200)
		facts[fact.user?.id ?: 0] = fact
	}

}




typealias Hook = () -> Unit

class ShutdownBuilder {
	private val scanner = Scanner(System.`in`)

	private val hooks = arrayListOf<Hook>()

	fun hook(hook: Hook) {
		hooks.add(hook)
	}

	fun pauseUntilEnter() {
		println("Press enter to continue")
		scanner.nextLine()
	}

	fun executeShutdown() {
		println("executing shutdowns")
		hooks.forEach(Hook::invoke)
	}
}

//utils
fun awaitEnter(body: ShutdownBuilder.() -> Unit) {
	val sb = ShutdownBuilder()
	sb.body()
	sb.pauseUntilEnter()
	sb.executeShutdown()
	println("bye!")
}

fun initJdbcTemplate(): JdbcTemplate {
	return JdbcTemplate(JdbcDataSource()
			.apply {
				setUrl("jdbc:h2:mem:facts_app;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false")
			})
			.apply {
				execute("CREATE TABLE USERS (ID INT AUTO_INCREMENT PRIMARY KEY, FIRST_NAME VARCHAR(64) NOT NULL, LAST_NAME VARCHAR(64) NOT NULL, GENDER VARCHAR(8) NOT NULL);")
				execute("CREATE TABLE FACTS (ID INT AUTO_INCREMENT PRIMARY KEY, VALUE_ TEXT NOT NULL, USER INT NOT NULL,  FOREIGN KEY (USER) REFERENCES USERS(ID) ON DELETE RESTRICT)")
			}
}
