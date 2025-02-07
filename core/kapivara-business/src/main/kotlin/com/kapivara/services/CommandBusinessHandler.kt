package com.kapivara.services

fun interface CommandBusinessHandler<T : CommandBusiness> {
    suspend fun store(commandBusiness: T)
}
