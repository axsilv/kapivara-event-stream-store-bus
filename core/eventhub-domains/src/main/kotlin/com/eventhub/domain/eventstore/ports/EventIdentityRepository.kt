package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.EventIdentity
import com.eventhub.domain.eventstore.EventIdentity.IdentityId
import com.eventhub.domain.eventstore.EventPublisher.PublisherId
import com.eventhub.domain.eventstore.EventSubscriber.SubscriberId

interface EventIdentityRepository {
    suspend fun store(eventIdentity: EventIdentity)

    suspend fun fetchPublisherId(identityId: IdentityId): PublisherId

    suspend fun fetchSubscribersId(identityId: IdentityId): List<SubscriberId>
}
