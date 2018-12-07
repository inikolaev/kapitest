package com.github.inikolaev.kapitest.matchers

data class MatchingResult(
    val match: Boolean,
    val name: String,
    val nameSuffix: String,
    val message: String,
    val value: Any?
)

open class Matcher<T>(
    protected val matchingResults: MutableList<MatchingResult>,
    private val name: String,
    protected val value: T?,
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

    infix fun isNotEqual(value: Any?) =
        matchingResults.add(MatchingResult(
            this.value != value,
            name,
            nameSuffix,
            "is not equal to",
            value
        ))

    protected fun match(match: Boolean, message: String, value: Any?) =
        match(match, name, nameSuffix, message, value)

    protected fun match(match: Boolean, name: String, message: String, value: Any?) =
        match(match, name, "", message, value)

    protected fun match(match: Boolean, name: String, nameSuffix: String, message: String, value: Any?) =
        matchingResults.add(MatchingResult(
            match,
            name,
            nameSuffix,
            message,
            value
        ))
}

class StringMatcher(
    matchingResults: MutableList<MatchingResult>,
    name: String,
    value: String?,
    nameSuffix: String = " "
): Matcher<String>(matchingResults, name, value, nameSuffix)

class StatusMatcher(
    matchingResults: MutableList<MatchingResult>,
    status: Int?
): Matcher<Int>(matchingResults, "status", status) {
    infix fun isEqual(value: Int) = super.isEqual(value)
    infix fun isEqual(value: String) = super.isEqual(value.toIntOrNull() ?: value)
    infix fun isNotEqual(value: Int) = super.isNotEqual(value)
    infix fun isNotEqual(value: String) = super.isNotEqual(value.toIntOrNull() ?: value)
    infix fun isGreaterThan(value: Int) = super.match(
        if (this.value == null) false else this.value > value,
        "is greater than",
        value
    )
    infix fun isGreaterOrEquals(value: Int) = super.match(
        if (this.value == null) false else this.value >= value,
        "is greater than",
        value
    )
    infix fun isLowerThan(value: Int) = super.match(
        if (this.value == null) false else this.value < value,
        "is lower than",
        value
    )
    infix fun isLowerOrEquals(value: Int) = super.match(
        if (this.value == null) false else this.value <= value,
        "is lower than",
        value
    )
}

class HeadersMatcher(
    matchingResults: MutableList<MatchingResult>,
    headers: Map<String, String>
): Matcher<Map<String, String>>(matchingResults, "headers", headers) {
    operator fun get(header: String): StringMatcher {
        return StringMatcher(matchingResults, header, this.value?.get(header.toLowerCase()), "header")
    }
}