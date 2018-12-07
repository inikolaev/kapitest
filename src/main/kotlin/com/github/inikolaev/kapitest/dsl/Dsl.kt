package com.github.inikolaev.kapitest.dsl

import com.github.inikolaev.kapitest.http.HttpClient
import com.github.inikolaev.kapitest.matchers.HeadersMatcher
import com.github.inikolaev.kapitest.matchers.MatchingResult
import com.github.inikolaev.kapitest.matchers.StatusMatcher
import com.github.inikolaev.kapitest.matchers.StringMatcher
import com.github.inikolaev.kapitest.reporter.ConsoleReporter

@DslMarker
annotation class KApiTestDslMarker

@KApiTestDslMarker
object KApiTest {
    fun scenario(name: String, block: Scenario.() -> Unit): Scenario {
        val matchingResults = mutableListOf<MatchingResult>()
        val scenario = Scenario(name, matchingResults)
        scenario.block()

        ConsoleReporter(name, matchingResults.toList()).report()

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
    var method: String = "get"
    var schema: String = "http"
    var host: String = "localhost"
    var port: Int = 80
    var path: String = "/"
    var params = mapOf<String, String>()
    var headers = mapOf<String, String>()
    var body: String? = null
}

@KApiTestDslMarker
class Response(
    val status: StatusMatcher,
    val headers: HeadersMatcher,
    val body: StringMatcher
)

class Promise(val request: Request, val matchingResults: MutableList<MatchingResult>) {
    infix fun then(block: Response.() -> Unit) {
        val response: Response = execute(request)
        response.block()
    }

    private fun execute(request: Request): Response {
        val httpResponse = HttpClient.request(
            request.method,
            request.schema,
            request.host,
            request.port,
            request.path,
            request.params,
            request.headers,
            request.body?.toByteArray()
        )
        return Response(
            StatusMatcher(matchingResults, httpResponse.status),
            HeadersMatcher(matchingResults, httpResponse.headers),
            StringMatcher(matchingResults, "body", httpResponse.body.toString()))
    }
}
