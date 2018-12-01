package com.github.inikolaev.kapitest.http

import com.mashape.unirest.http.Unirest

data class HttpResponse(
    val status: Int,
    val headers: Map<String, String>,
    val body: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpResponse

        if (status != other.status) return false
        if (headers != other.headers) return false
        if (!body.contentEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status
        result = 31 * result + headers.hashCode()
        result = 31 * result + body.contentHashCode()
        return result
    }
}

object HttpClient {
    fun request(
        method: String,
        schema: String,
        host: String,
        port: Int,
        path: String,
        params: Map<String, String> = mapOf(),
        headers: Map<String, String> = mapOf(),
        body: ByteArray? = null
    ): HttpResponse {
        val response = when (method.toLowerCase()) {
            "get" -> {
                Unirest.get("$schema://$host:$port$path")
                    .queryString(params)
                    .headers(headers)
                    .asBinary()
            }

            "post" -> {
                Unirest.post("$schema://$host:$port$path")
                    .queryString(params)
                    .headers(headers)
                    .body(body)
                    .asBinary()
            }

            else -> throw IllegalArgumentException("Unrecognized HTTP method: $method")
        }

        return HttpResponse(
            status = response.status,
            headers = response.headers.map { (name, values) ->
                Pair(name.toLowerCase(), values.first())
            }.toMap(),
            body = response.body.readBytes()
        )
    }
}