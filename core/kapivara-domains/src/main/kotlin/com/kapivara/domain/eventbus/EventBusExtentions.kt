package com.kapivara.domain.eventbus

import com.eventhub.domain.eventbus.EventBusBucket.EventBusBucketId

fun Long.toBucketId(): EventBusBucketId = EventBusBucketId(this)
