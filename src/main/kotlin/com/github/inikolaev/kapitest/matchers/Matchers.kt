package com.github.inikolaev.kapitest.matchers

import com.github.inikolaev.kapitest.cyan

data class MatchingResult(val match: Boolean, val message: String)

open class Matcher(
    private val matchingResults: MutableList<MatchingResult>,
    private val name: String,
    private val value: Any?,
    private val nameSuffix: String = ""
) {
    infix fun isEqual(value: Any?) =
        // TODO: we should somehow remove coloring from here,
        //       reporter should be responsible for proper coloring
        matchingResults.add(MatchingResult(
            this.value == value,
            "${cyan(name)}$nameSuffix is equal to ${cyan(value)}"
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
        return StringMatcher(matchingResults, header, headers[header], " header")
    }
}