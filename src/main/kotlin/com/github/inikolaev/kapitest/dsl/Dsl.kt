package com.github.inikolaev.kapitest.dsl

import com.github.inikolaev.kapitest.green
import com.github.inikolaev.kapitest.matchers.HeadersMatcher
import com.github.inikolaev.kapitest.matchers.MatchingResult
import com.github.inikolaev.kapitest.matchers.StatusMatcher
import com.github.inikolaev.kapitest.red
import com.github.inikolaev.kapitest.yellow

@DslMarker
annotation class KApiTestDslMarker

@KApiTestDslMarker
object KApiTest {
    fun scenario(name: String, block: Scenario.() -> Unit): Scenario {
        val matchingResults = mutableListOf<MatchingResult>()
        val scenario = Scenario(name, matchingResults)
        scenario.block()

        val matching = matchingResults.all(MatchingResult::match)

        if (matching) {
            println("${green("\u2714")} ${yellow(name)}")
        } else {
            println("${red("\u2718")} ${yellow(name)}")
        }

        matchingResults.forEach {
            if (it.match) {
                println("\t${green("\u2714")} ${it.message}")
            } else {
                println("\t${red("\u2718")} ${it.message}")
            }
        }

        println()
        return scenario
    }
}

@KApiTestDslMarker
class Scenario(
    val name: String,
    private val matchingResults: MutableList<MatchingResult>
) {
    fun given(block: Request.() -> Unit): Promise {
        val request = Request()
        request.block()
        return Promise(request, matchingResults)
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

class Promise(val request: Request, val matchingResults: MutableList<MatchingResult>) {
    fun then(block: Response.() -> Unit) {
        val response: Response = execute(request)
        response.block()
    }

    fun execute(request: Request): Response =
        Response(
            StatusMatcher(matchingResults, 200),
            HeadersMatcher(matchingResults, mapOf("content-type" to "application/json")))
}
