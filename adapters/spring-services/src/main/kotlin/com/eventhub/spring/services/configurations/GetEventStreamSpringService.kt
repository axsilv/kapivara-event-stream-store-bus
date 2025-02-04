package com.eventhub.spring.services.configurations

import com.eventhub.services.eventstore.EventStreamQueryResult
import com.eventhub.services.eventstore.GetEventStreamService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetEventStreamSpringService(
    eventStoreRepository: EventStoreRepository,
) : GetEventStreamService(
        eventStoreRepository = eventStoreRepository,
    ) {
    @Transactional
    override suspend fun get(eventStreamId: UUID): EventStreamQueryResult = super.get(eventStreamId = eventStreamId)
}
