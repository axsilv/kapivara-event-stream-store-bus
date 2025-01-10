package com.eventhub.domain.eventstore

import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.EventTestFixture.event
import com.eventhub.domain.eventstore.EventTestFixture.eventStreamUuid

object EventStreamTestFixture {
    fun eventStream() =
        EventStream(
            eventStreamId = EventStreamId(eventStreamUuid),
            events = listOf(event(shouldSendToEventBus = true), event(shouldSendToEventBus = true)),
        )
}
