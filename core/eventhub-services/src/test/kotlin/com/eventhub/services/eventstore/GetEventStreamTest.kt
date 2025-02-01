package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventStream.AggregateId
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
                        mockkStatic(AggregateId::toUUID)
                        val eventStreamUuid = mockk<UUID>()
                        val aggregateId = mockk<AggregateId>()

                        every { eventStreamUuid.toEventStreamId() } returns aggregateId
                        coEvery {
                            aggregateId.get(eventStoreRepository = any())
                        } returns
                            EventStream(
                                id = aggregateId,
                                eventMessages = listOf(event()),
                            )
                        every { aggregateId.toUUID() } returns eventStreamUuid

                        service.get(eventStreamId = eventStreamUuid)

                        verify { eventStreamUuid.toEventStreamId() }
                        coVerify { aggregateId.get(eventStoreRepository = eventStoreRepository) }
                        verify { aggregateId.toUUID() }
                        confirmVerified(aggregateId, eventStreamUuid)
                    }
                }
            }
        }
    })
