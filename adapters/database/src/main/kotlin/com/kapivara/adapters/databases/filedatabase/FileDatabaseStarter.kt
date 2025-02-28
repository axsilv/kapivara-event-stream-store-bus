package com.kapivara.adapters.databases.filedatabase

import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.dataPath
import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.streamPath
import io.github.oshai.kotlinlogging.KotlinLogging

object FileDatabaseStarter {
    private val log = KotlinLogging.logger { }

    private val paths =
        setOf(
            dataPath(),
            streamPath(),
        )

//    fun run() {
//        paths.forEach { path ->
//            if (Files.exists(Path.of(path)).not()) {
//                File(path).mkdirs()
//                log.info { "created $path" }
//            }
//        }
//    }
}
