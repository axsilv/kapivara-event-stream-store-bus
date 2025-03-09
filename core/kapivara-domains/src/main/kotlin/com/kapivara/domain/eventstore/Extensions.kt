package com.kapivara.domain.eventstore

import com.kapivara.domain.eventstore.Event.EventId
import com.kapivara.domain.eventstore.Stream.StreamId
import java.util.UUID

fun UUID.toEventId() = EventId(this)

fun UUID.toStreamId() = StreamId(this)
