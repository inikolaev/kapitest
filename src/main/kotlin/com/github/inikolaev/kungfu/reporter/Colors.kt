package com.github.inikolaev.kungfu.reporter

fun color(color: Int, text: Any?) =
    "\u001B[${color}m$text\u001B[0m"

fun red(text: Any?) = color(31, text)
fun yellow(text: Any?) = color(33, text)
fun green(text: Any?) = color(32, text)
fun cyan(text: Any?) = color(36, text)