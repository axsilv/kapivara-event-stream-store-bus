package com.eventhub.domain

import java.util.UUID

abstract class Identifier(
    private val value: UUID,
) {
    fun toUUID() = value

    override fun toString() = value.toString()
}
