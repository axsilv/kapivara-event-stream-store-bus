package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.eventhub.domain.eventstore.EventIdentity
import com.eventhub.domain.eventstore.EventPublisher
import com.eventhub.domain.eventstore.EventSubscriber
import com.eventhub.domain.eventstore.ports.EventIdentityRepository
import org.springframework.stereotype.Repository

@Repository
class R2dbcEventIdentityRepository : EventIdentityRepository {
    override suspend fun store(eventIdentity: EventIdentity) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPublisherId(identityId: EventIdentity.IdentityId): EventPublisher.PublisherId {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSubscribersId(identityId: EventIdentity.IdentityId): List<EventSubscriber.SubscriberId> {
        TODO("Not yet implemented")
    }
}
