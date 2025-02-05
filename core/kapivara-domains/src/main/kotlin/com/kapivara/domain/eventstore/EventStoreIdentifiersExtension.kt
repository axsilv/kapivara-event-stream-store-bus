package com.kapivara.domain.eventstore

import java.util.UUID

fun UUID.toEventMessageId() = EventMessage.EventMessageId(this)

fun Long.toIdentityId() = Identity.IdentityId(this)

fun Long.toPublisherId() = Publisher.PublisherId(this)

fun UUID.toEventStreamId() = EventStream.EventStreamId(this)
