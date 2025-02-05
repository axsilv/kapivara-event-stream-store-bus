package com.kapivara.adapters.databases.filedatabase

import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.bucketsPath
import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.dataPath
import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.hidePath
import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.identitiesPath
import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.publishersPath
import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.streamPath
import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.subscribersPath
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
