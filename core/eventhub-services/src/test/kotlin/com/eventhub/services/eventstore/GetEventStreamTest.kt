package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import com.eventhub.domain.eventstore.toEventStreamId
import com.eventhub.services.eventstore.EventTestFixture.event
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
                        mockkStatic(UUID::toEventStreamId)
                        mockkStatic(EventStreamId::toUUID)
                        val eventStreamUuid = mockk<UUID>()
                        val eventStreamId = mockk<EventStreamId>()

                        every { eventStreamUuid.toEventStreamId() } returns eventStreamId
                        coEvery {
                            eventStreamId.get(eventStoreRepository = any())
                        } returns
                            EventStream(
                                eventStreamId = eventStreamId,
                                events = listOf(event()),
                            )
                        every { eventStreamId.toUUID() } returns eventStreamUuid

                        service.get(eventStreamId = eventStreamUuid)

                        verify { eventStreamUuid.toEventStreamId() }
                        coVerify { eventStreamId.get(eventStoreRepository = eventStoreRepository) }
                        verify { eventStreamId.toUUID() }
                        confirmVerified(eventStreamId, eventStreamUuid)
                    }
                }
            }
        }
    })
