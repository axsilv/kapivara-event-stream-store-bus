package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.EventStream

fun EventStore.EventId.toEventId() = Event.EventId(value = this.toUUID())

fun EventStore.EventStreamId.toEventStreamId() = EventStream.EventStreamId(value = this.toUUID())

fun EventStore.OwnerId.toOwnerId() = Event.OwnerId(value = this.toUUID())

fun EventStore.IdentityId.toIdentityId() = Event.IdentityId(value = this.toUUID())

fun Event.EventId.toEventId() = EventStore.EventId(value = this.toUUID())

fun EventStream.EventStreamId.toEventStreamId() = EventStore.EventStreamId(value = this.toUUID())

fun Event.OwnerId.toOwnerId() = EventStore.OwnerId(value = this.toUUID())

fun Event.IdentityId.toIdentityId() = EventStore.IdentityId(value = this.toUUID())
