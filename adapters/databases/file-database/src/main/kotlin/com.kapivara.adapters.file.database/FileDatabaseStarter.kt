package com.kapivara.adapters.file.database

import com.kapivara.adapters.file.database.FileDatabaseVariables.bucketsPath
import com.kapivara.adapters.file.database.FileDatabaseVariables.dataPath
import com.kapivara.adapters.file.database.FileDatabaseVariables.hidePath
import com.kapivara.adapters.file.database.FileDatabaseVariables.identitiesPath
import com.kapivara.adapters.file.database.FileDatabaseVariables.publishersPath
import com.kapivara.adapters.file.database.FileDatabaseVariables.streamPath
import com.kapivara.adapters.file.database.FileDatabaseVariables.subscribersPath
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

object FileDatabaseStarter {
    private val log = KotlinLogging.logger { }

    private val paths =
        setOf(
            dataPath(),
            streamPath(),
            bucketsPath(),
            hidePath(),
            identitiesPath(),
            publishersPath(),
            subscribersPath(),
        )

    fun run() {
        paths.forEach { path ->
            if (Files.exists(Path.of(path)).not()) {
                File(path).mkdirs()
                log.info { "created $path" }
            }
        }
    }
}
