package com.kapivara.domain.eventstore

import com.kapivara.domain.Identifier
import com.kapivara.domain.eventstore.ports.PublisherRepository

data class Publisher(
    val id: PublisherId,
    val publisherName: String,
) {
    companion object {
        suspend fun fetch(
            id: PublisherId,
            publisherRepository: PublisherRepository,
        ) = publisherRepository.fetch(id)
    }

    data class PublisherId(
        val value: Long,
    ) : Identifier<Long>(value = value)

    suspend fun store(publisherRepository: PublisherRepository) {
        publisherRepository.store(this)
    }
}
