package com.eventhub.domain.eventbus

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.Event
import java.util.UUID

data class Message(
    val messageId: MessageId,
    val event: Event,
    val subscribers: List<Subscriber>,
    val notifications: List<Notification>,
) {
    data class MessageId(
        val value: UUID,
    ) : Identifier(value = value)
}
