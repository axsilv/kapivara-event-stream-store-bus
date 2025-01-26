package com.eventhub.domain.eventbus

import com.eventhub.domain.eventbus.EventBusBucket.BucketId
import java.util.UUID

fun UUID.toBucketId(): BucketId = BucketId(this)
