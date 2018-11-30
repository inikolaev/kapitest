import java.util.*

@DslMarker
annotation class KApiTestDslMarker

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Name(val value: String)

@KApiTestDslMarker
object KApiTest {
    fun scenario(name: String, block: Scenario.() -> Unit): Scenario {
        val scenario = Scenario(name)
        scenario.block()
        return scenario
    }
}

@KApiTestDslMarker
class Scenario(val name: String) {
    fun given(block: Request.() -> Unit): Promise {
        val request = Request()
        request.block()
        return Promise(request)
    }
}

@KApiTestDslMarker
class Request {
    var schema: String? = "http"
    var host: String? = "localhost"
    var port: Int? = 80
    var params = mutableMapOf<String, String>()
    var headers = mutableMapOf<String, String>()
    var body: Any? = null
}

@KApiTestDslMarker
class Response(
    @Name("status")
    val status: Int?,
    val headers: Map<String, String> = mapOf()
) {
    private val fields = IdentityHashMap<Any, String>()

    init {
        fields[status] = "status"
    }

    infix fun Any.isEqual(other: Any): Boolean {
        return this == other
    }

    @JvmName("isEqual__optional")
    infix fun Any?.isEqual(other: Any): Boolean {
        val name = fields[this] ?: this

        if (this == other) {
            println("\u001B[32m\u2714\u001B[0m \u001B[36m$name\u001B[0m is equal to \u001B[36m$other\u001B[0m")
        } else {
            println("\u001B[31m\u2718\u001B[0m \u001B[36m$name\u001B[0m is equal to \u001B[36m$other\u001B[0m")
        }

        return this == other
    }

    @JvmName("isNotEqual__optional")
    infix fun Any?.isNotEqual(other: Any): Boolean {
        val name = fields[this] ?: this

        if (this != other) {
            println("\u001B[32m\u2714\u001B[0m \u001B[36m$name\u001B[0m is not equal to \u001B[36m$other\u001B[0m")
        } else {
            println("\u001B[31m\u2718\u001B[0m \u001B[36m$name\u001B[0m is not equal to \u001B[36m$other\u001B[0m")
        }

        return this != other
    }
}

class Promise(val request: Request) {
    fun then(block: Response.() -> Unit) {
        val response: Response = execute(request)
        response.block()
    }

    fun execute(request: Request): Response = Response(200)
}

fun main(args: Array<String>) {
    KApiTest.scenario("test") {
        given {
            schema = "https"
            host = "localhost"
            port = 443
        }.then {
            status isEqual 200
            headers["test"] isNotEqual "test"
        }
    }
}
