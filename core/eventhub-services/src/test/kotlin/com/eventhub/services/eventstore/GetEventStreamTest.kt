package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.toEventId
import com.eventhub.domain.eventstore.toEventStreamId
import com.eventhub.ports.eventstore.EventStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import java.util.UUID

class GetEventStreamTest :
    BehaviorSpec({
        context("Get a stream of event") {
            given("A event stream id") {
                `when`("Calls get stream") {
                    then("Returns the stream") {
                        val eventStoreRepository = mockk<EventStoreRepository>()
                        val service =
                            GetEventStreamService(
                                eventStoreRepository = eventStoreRepository,
                            )
                        mockkStatic(UUID::toEventId)
                        val eventStreamUuid = mockk<UUID>()
                        val eventStreamId = mockk<EventStreamId>()

                        every { eventStreamUuid.toEventStreamId() } returns eventStreamId
                        coEvery {
                            eventStreamId.get(eventStoreRepository = any())
                        } returns
                            EventStream(
                                eventStreamId = eventStreamId,
                                events = listOf(mockk<Event>()),
                            )

                        service.get(eventStreamId = eventStreamUuid)

                        verify { eventStreamUuid.toEventStreamId() }
                        coVerify { eventStreamId.get(eventStoreRepository = eventStoreRepository) }
                        confirmVerified(eventStreamId, eventStreamUuid)
                    }
                }
            }
        }
    })
