package com.kapivara.adapters.file.database

import com.eventhub.domain.eventstore.EventIdentity
import com.eventhub.domain.eventstore.Publisher
import com.eventhub.domain.eventstore.Subscriber
import com.eventhub.domain.eventstore.ports.EventIdentityRepository

class FileDatabaseEventIdentityRepository : EventIdentityRepository {
    override suspend fun store(eventIdentity: EventIdentity) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPublisherId(identityId: EventIdentity.IdentityId): Publisher.PublisherId? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSubscribersId(identityId: EventIdentity.IdentityId): List<Subscriber.SubscriberId> {
        TODO("Not yet implemented")
    }

    override suspend fun appendSubscriberId(
        eventIdentity: EventIdentity,
        subscriberId: Subscriber.SubscriberId,
    ) {
        TODO("Not yet implemented")
    }
}
