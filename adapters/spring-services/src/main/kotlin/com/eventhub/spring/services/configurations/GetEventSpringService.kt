package com.eventhub.spring.services.configurations

import com.eventhub.services.eventstore.EventQueryResult
import com.eventhub.services.eventstore.GetEventService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetEventSpringService(
    eventStoreRepository: EventStoreRepository,
) : GetEventService(
        eventStoreRepository = eventStoreRepository,
    ) {
    @Transactional
    override suspend fun get(
        eventId: UUID,
        ownerId: UUID,
    ): EventQueryResult? =
        super.get(
            eventId = eventId,
            ownerId = ownerId,
        )
}
