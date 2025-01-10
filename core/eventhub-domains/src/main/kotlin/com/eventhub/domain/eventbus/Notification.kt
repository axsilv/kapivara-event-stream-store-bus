package com.eventhub.domain.eventbus

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
        val id: UUID,
    )
}
