package com.kapivara.domain

open class Identifier<T>(
    private val value: T,
) {
    override fun toString() = value.toString()
}
