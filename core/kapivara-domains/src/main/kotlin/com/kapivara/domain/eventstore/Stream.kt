package com.kapivara.domain.eventstore

import com.kapivara.domain.Identifier
import com.kapivara.domain.eventbus.EventBusBucket.Companion.deliverToSubscriber
import com.kapivara.domain.eventbus.ports.EventBusBucketRepository
import com.kapivara.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.kapivara.domain.eventstore.Message.MessageId
import com.kapivara.domain.eventstore.ports.IdentityRepository
import com.kapivara.domain.eventstore.ports.StreamRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.util.UUID

data class Stream(
    val eventMessages: Set<Message>,
) {
    companion object {
        private val log = KotlinLogging.logger { }

        suspend fun deliverStream(
            streamRepository: StreamRepository,
            identityRepository: IdentityRepository,
            streamId: StreamId,
            eventBusBucketRepository: EventBusBucketRepository,
            eventBusDeliveryControlRepository: EventBusDeliveryControlRepository,
        ) = coroutineScope {
            try {
                streamRepository
                    .fetch(streamId)
                    ?.eventMessages
                    ?.map { it.identityId }
                    ?.map { async { identityRepository.fetchSubscribersId(it) } }
                    ?.awaitAll()
                    ?.flatten()
                    ?.map { subscriberId ->
                        launch {
                            deliverToSubscriber(
                                eventBusBucketRepository,
                                subscriberId,
                                streamId,
                                eventBusDeliveryControlRepository,
                            )
                        }
                    }?.joinAll()
            } catch (e: Exception) {
                log.error { "event stream error ${e.localizedMessage}" }

                // TODO - add metrics
                throw e
            }
        }

        suspend fun fetch(
            streamId: StreamId,
            messageId: MessageId,
            streamRepository: StreamRepository,
        ) = streamRepository.fetch(
            streamId = streamId,
            messageId = messageId,
        )

        suspend fun fetch(
            streamId: StreamId,
            streamRepository: StreamRepository,
        ) = streamRepository.fetch(
            streamId = streamId,
        )

        fun UUID.toStreamId() = StreamId(this)
    }

    class StreamId(
        val value: UUID,
    ) : Identifier<UUID>(value = value)
}
