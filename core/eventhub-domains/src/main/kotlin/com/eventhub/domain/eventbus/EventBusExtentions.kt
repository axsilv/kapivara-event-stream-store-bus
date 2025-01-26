package com.eventhub.domain.eventbus

import com.eventhub.domain.eventbus.Bucket.BucketId
import com.eventhub.domain.eventstore.Message
import java.util.UUID

fun UUID.toBucketId(): BucketId = BucketId(this)