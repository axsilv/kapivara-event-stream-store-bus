package com.kapivara.services

fun interface CommandHandler<T : Command> {
    suspend fun store(command: T)
}
