package com.github.inikolaev.kapitest.matchers

data class MatchingResult(
    val match: Boolean,
    val name: String,
    val nameSuffix: String,
    val message: String,
    val value: Any?
)

open class Matcher(
    private val matchingResults: MutableList<MatchingResult>,
    private val name: String,
    private val value: Any?,
    private val nameSuffix: String = ""
) {
    infix fun isEqual(value: Any?) =
        matchingResults.add(MatchingResult(
            this.value == value,
            name,
            nameSuffix,
            "is equal to",
            value
        ))
}

class StringMatcher(
    matchingResults: MutableList<MatchingResult>,
    name: String,
    value: String?,
    nameSuffix: String = " "
): Matcher(matchingResults, name, value, nameSuffix)

class StatusMatcher(
    matchingResults: MutableList<MatchingResult>,
    status: Int?
): Matcher(matchingResults, "status", status)

class HeadersMatcher(
    private val matchingResults: MutableList<MatchingResult>,
    private val headers: Map<String, String>
): Matcher(matchingResults, "headers", headers) {
    operator fun get(header: String): StringMatcher {
        return StringMatcher(matchingResults, header, headers[header.toLowerCase()], "header")
    }
}