package com.github.inikolaev.kapitest.dsl

import com.github.inikolaev.kapitest.green
import com.github.inikolaev.kapitest.matchers.HeadersMatcher
import com.github.inikolaev.kapitest.matchers.StatusMatcher
import com.github.inikolaev.kapitest.yellow

@DslMarker
annotation class KApiTestDslMarker

@KApiTestDslMarker
object KApiTest {
    fun scenario(name: String, block: Scenario.() -> Unit): Scenario {
        val scenario = Scenario(name)
        println("${green("\u2714")} ${yellow(name)}")
        scenario.block()
        println()
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
    var schema: String = "http"
    var host: String = "localhost"
    var port: Int = 80
    var params = mutableMapOf<String, String>()
    var headers = mutableMapOf<String, String>()
    var body: Any? = null
}

@KApiTestDslMarker
class Response(
    val status: StatusMatcher,
    val headers: HeadersMatcher
)

class Promise(val request: Request) {
    fun then(block: Response.() -> Unit) {
        val response: Response = execute(request)
        response.block()
    }

    fun execute(request: Request): Response =
        Response(
            StatusMatcher(200),
            HeadersMatcher(mapOf("content-type" to "application/json")))
}
