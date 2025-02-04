package com.kapivara.adapters.file.database

import com.eventhub.domain.eventstore.Subscriber
import com.eventhub.domain.eventstore.ports.EventSubscriberRepository
import org.springframework.stereotype.Service

@Service

class FileDatabaseEventSubscriberRepository: EventSubscriberRepository {
    override suspend fun store(subscriber: Subscriber) {
        TODO("Not yet implemented")
    }
}
