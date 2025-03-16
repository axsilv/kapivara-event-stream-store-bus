package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.ports.EventStorageOptimizationRepository
import com.kapivara.domain.eventstore.ports.EventStorageRepository
import com.kapivara.services.CommandBusinessHandler

class StoreStreamHandler(
    private val eventStorageRepository: EventStorageRepository,
    private val eventStorageOptimizationRepository: EventStorageOptimizationRepository,
) : CommandBusinessHandler<StoreStream> {
    override suspend fun store(commandBusiness: StoreStream) =
        commandBusiness
            .toStream()
            .create(
                eventStorageRepository = eventStorageRepository,
                eventStorageOptimizationRepository = eventStorageOptimizationRepository,
            )
}
