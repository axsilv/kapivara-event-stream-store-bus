package com.eventhub.domain.eventbus

import com.eventhub.domain.eventbus.EventBusBucket.EventBusBucketId
import java.util.UUID

fun UUID.toBucketId(): EventBusBucketId = EventBusBucketId(this)
