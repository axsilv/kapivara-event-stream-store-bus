package com.kapivara.domain.eventstore.ports

import com.kapivara.domain.eventstore.Identity
import com.kapivara.domain.eventstore.Identity.IdentityId
import com.kapivara.domain.eventstore.Publisher.PublisherId
import com.kapivara.domain.eventstore.Subscriber.SubscriberId

interface EventIdentityRepository {
    suspend fun store(identity: Identity)

    suspend fun fetchPublisherId(identityId: IdentityId): PublisherId?

    suspend fun fetchSubscribersId(identityId: IdentityId): Set<SubscriberId>

    suspend fun appendSubscriberId(
        identityId: IdentityId,
        subscriberId: SubscriberId,
    )
}
