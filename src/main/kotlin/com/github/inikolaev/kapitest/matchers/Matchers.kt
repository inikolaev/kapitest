package com.github.inikolaev.kapitest.matchers

import com.github.inikolaev.kapitest.cyan
import com.github.inikolaev.kapitest.green
import com.github.inikolaev.kapitest.red

open class Matcher(
    private val name: String,
    private val value: Any?,
    private val nameSuffix: String = ""
) {
    infix fun isEqual(value: Any?): Boolean {
        if (this.value == value) {
            println("\t${green("\u2714")} ${cyan(name)}$nameSuffix is equal to ${cyan(value)}")
        } else {
            println("\t${red("\u2718")} ${cyan(name)}$nameSuffix is equal to ${cyan(value)}")
        }

        return this.value == value
    }
}

class StringMatcher(name: String, value: String?, nameSuffix: String = " "): Matcher(name, value, nameSuffix)
class StatusMatcher(status: Int?): Matcher("status", status)
class HeadersMatcher(val headers: Map<String, String>): Matcher("headers", headers) {
    operator fun get(header: String): StringMatcher {
        return StringMatcher(header, headers[header], " header")
    }
}