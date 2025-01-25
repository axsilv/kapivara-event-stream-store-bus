package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.Event.IdentityId

interface IdentityRepository {
    suspend fun exists(identityId: IdentityId): Boolean
}
