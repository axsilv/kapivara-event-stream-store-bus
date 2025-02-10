package com.kapivara.domain.eventstore

import java.util.UUID

fun UUID.toEventMessageId() = Message.MessageId(this)

fun Long.toIdentityId() = Identity.IdentityId(this)

fun Long.toPublisherId() = Publisher.PublisherId(this)

fun UUID.toEventStreamId() = Stream.StreamId(this)
