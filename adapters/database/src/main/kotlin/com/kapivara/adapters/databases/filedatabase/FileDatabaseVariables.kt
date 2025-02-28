package com.kapivara.adapters.databases.filedatabase

import java.nio.file.Path

object FileDatabaseVariables {
    fun dataPath(): Path =
        System.getenv("FILE_PATH")?.let { Path.of(it, "") }
            ?: System.getProperty("FILE_PATH")?.let { Path.of(it, "") }
            ?: Path.of("${System.getProperty("user.home")}", "kapivara", "data")

    fun streamPath() = dataPath().plus(Path.of("eventstore", "streams"))
}
