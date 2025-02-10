package com.kapivara.domain.eventstore.ports

import com.kapivara.domain.eventstore.Message
import com.kapivara.domain.eventstore.Message.MessageId
import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.Stream.StreamId

interface StreamRepository {
    suspend fun store(
        message: Message,
        useCache: Boolean = false,
    )

    suspend fun fetch(
        streamId: StreamId,
        useCache: Boolean = false,
    ): Stream?

    suspend fun fetch(
        messageId: MessageId,
        streamId: StreamId,
        useCache: Boolean = false,
    ): Message?
}
