package com.eventhub.domain.eventbus

import com.eventhub.domain.Identifier
import kotlinx.datetime.Instant
import java.util.UUID

data class Notification(
    val notificationId: NotificationId,
    val subscriber: Subscriber,
    val isFailure: Boolean,
    val metadata: Map<String, String>,
    val occurredOn: Instant,
) {
    data class NotificationId(
        val value: UUID,
    ) : Identifier(value = value)
}
