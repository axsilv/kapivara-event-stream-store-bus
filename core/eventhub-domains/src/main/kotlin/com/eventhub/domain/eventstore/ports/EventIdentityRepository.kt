package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.Identity
import com.eventhub.domain.eventstore.Identity.IdentityId
import com.eventhub.domain.eventstore.Publisher.PublisherId
import com.eventhub.domain.eventstore.Subscriber.SubscriberId

interface EventIdentityRepository {
    suspend fun store(identity: Identity)

    suspend fun fetchPublisherId(identityId: IdentityId): PublisherId?

    suspend fun fetchSubscribersId(identityId: IdentityId): Set<SubscriberId>

    suspend fun appendSubscriberId(
        identityId: IdentityId,
        subscriberId: SubscriberId,
    )
}
