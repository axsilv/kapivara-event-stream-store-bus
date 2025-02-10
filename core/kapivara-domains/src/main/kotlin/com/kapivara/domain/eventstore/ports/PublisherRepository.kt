package com.kapivara.domain.eventstore.ports

import com.kapivara.domain.eventstore.Publisher
import com.kapivara.domain.eventstore.Publisher.PublisherId

interface PublisherRepository {
    suspend fun store(publisher: Publisher)

    suspend fun fetch(publisherId: PublisherId): Publisher?
}
