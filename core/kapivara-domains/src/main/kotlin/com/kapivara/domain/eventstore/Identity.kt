package com.kapivara.domain.eventstore

import com.kapivara.domain.Identifier
import com.kapivara.domain.eventstore.Publisher.PublisherId
import com.kapivara.domain.eventstore.Subscriber.SubscriberId
import com.kapivara.domain.eventstore.ports.IdentityRepository

data class Identity(
    val id: IdentityId,
    val name: String,
    val publisherId: PublisherId,
    val eventSubscribers: Set<SubscriberId>,
    val metadata: Map<String, String>,
) {
    companion object {
        suspend fun fetch(
            identityRepository: IdentityRepository,
            identityId: IdentityId,
        ): Identity? = identityRepository.fetch(identityId)

        fun Long.toIdentityId() = IdentityId(this)
    }

    data class IdentityId(
        val value: Long,
    ) : Identifier<Long>(value = value)

    suspend fun store(identityRepository: IdentityRepository) {
        identityRepository.store(this)
    }
}
