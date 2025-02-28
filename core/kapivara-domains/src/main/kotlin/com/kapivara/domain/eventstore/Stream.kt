package com.kapivara.domain.eventstore

import com.kapivara.domain.Identifier
import com.kapivara.domain.eventstore.ports.EventStorageOptimizationRepository
import com.kapivara.domain.eventstore.ports.EventStorageRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.UUID
import kotlin.time.measureTime

data class Stream(
    val id: StreamId,
    val contextName: String,
    val systemName: String,
    val streamType: String,
    val eventMessages: Set<Message>,
    val createdAt: Long,
) {
    companion object {
        private val log = KotlinLogging.logger { }

        suspend fun fetch(
            id: StreamId,
            contextName: String,
            systemName: String,
            streamType: String,
            eventStorageRepository: EventStorageRepository,
        ) = eventStorageRepository.fetchLast(
            arrangement = linkedSetOf(contextName, systemName, streamType, id.value.toString()),
        )
    }

    class StreamId(
        val value: UUID,
    ) : Identifier<UUID>(value = value)

    suspend fun create(
        eventStorageRepository: EventStorageRepository,
        eventStorageOptimizationRepository: EventStorageOptimizationRepository,
    ) {
        measureTime {
            eventStorageOptimizationRepository.store(arrangement())
            eventStorageRepository.append(this)
        }.let { duration ->
            log.debug {
                "Measured time for creating a stream $duration"
            }
        }
    }

    fun arrangement(): LinkedHashSet<String> =
        linkedSetOf(
            contextName,
            systemName,
            streamType,
            id.value.toString(),
        )

    fun version() =
        if (this.eventMessages.isNotEmpty()) {
            this.eventMessages.maxOf { it.position }
        } else {
            0
        }
}
