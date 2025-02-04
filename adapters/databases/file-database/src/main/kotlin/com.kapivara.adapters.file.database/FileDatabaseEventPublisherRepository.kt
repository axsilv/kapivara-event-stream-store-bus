package com.kapivara.adapters.file.database

import com.eventhub.domain.eventstore.Publisher
import com.eventhub.domain.eventstore.ports.EventPublisherRepository
import org.springframework.stereotype.Service

@Service

class FileDatabaseEventPublisherRepository : EventPublisherRepository {
    override suspend fun store(publisher: Publisher) {
        TODO("Not yet implemented")
    }
}
