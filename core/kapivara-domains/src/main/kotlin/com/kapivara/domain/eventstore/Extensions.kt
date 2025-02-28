package com.kapivara.domain.eventstore

import com.kapivara.domain.eventstore.Message.MessageId
import com.kapivara.domain.eventstore.Stream.StreamId
import java.util.UUID

fun UUID.toMessageId() = MessageId(this)

fun UUID.toStreamId() = StreamId(this)
