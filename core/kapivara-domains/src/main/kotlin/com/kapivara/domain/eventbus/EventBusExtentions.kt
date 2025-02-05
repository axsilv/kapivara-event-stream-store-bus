package com.kapivara.domain.eventbus

import com.kapivara.domain.eventbus.EventBusBucket.EventBusBucketId

fun Long.toBucketId(): EventBusBucketId = EventBusBucketId(this)
