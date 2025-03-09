package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.ports.EventStorageOptimizationRepository
import com.kapivara.domain.eventstore.ports.EventStorageRepository
import com.kapivara.domain.eventstore.toStreamId
import com.kapivara.services.CommandBusinessHandler
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.datetime.Clock.System.now

class StoreStreamHandler(
    private val eventStorageRepository: EventStorageRepository,
    private val eventStorageOptimizationRepository: EventStorageOptimizationRepository,
) : CommandBusinessHandler<StoreStream> {
    override suspend fun store(commandBusiness: StoreStream) =
        with(commandBusiness) {
            Stream(
                id = id.toStreamId(),
                contextName = contextName ?: "defaultContextName",
                systemName = systemName ?: "defaultSystemName",
                streamType = streamType ?: "defaultStreamType",
                events = persistentSetOf(),
                createdAt = now().toEpochMilliseconds(),
            ).create(
                eventStorageRepository = eventStorageRepository,
                eventStorageOptimizationRepository = eventStorageOptimizationRepository,
            )
        }
}
