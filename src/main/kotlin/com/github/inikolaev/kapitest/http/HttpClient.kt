package com.github.inikolaev.kapitest.http

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
        params: Map<String, String> = mapOf(),
        headers: Map<String, String> = mapOf(),
        body: ByteArray? = null
    ): HttpResponse {
        return HttpResponse(
            status = 200,
            headers = mapOf("content-type" to "application/json"),
            body = "simple string body".toByteArray()
        )
    }
}