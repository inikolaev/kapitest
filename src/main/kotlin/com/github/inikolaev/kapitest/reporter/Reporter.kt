package com.github.inikolaev.kapitest.reporter

import com.github.inikolaev.kapitest.matchers.MatchingResult

abstract class Reporter(
    protected val name: String,
    protected val matchingResults: List<MatchingResult>
) {
    abstract fun report()
}