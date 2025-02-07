package com.kapivara.adapters.databases.filedatabase

import kotlin.math.absoluteValue

fun <T> number(t: T): Int {
    val hashCode = t.hashCode()
    return (hashCode % 101).absoluteValue
}
