package com.github.inikolaev.kungfu.reporter

import com.github.inikolaev.kungfu.matchers.MatchingResult

abstract class Reporter(
    protected val name: String,
    protected val matchingResults: List<MatchingResult>
) {
    abstract fun report()
}