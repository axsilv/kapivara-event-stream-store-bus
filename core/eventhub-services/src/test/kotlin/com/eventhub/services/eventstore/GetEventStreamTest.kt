package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.ports.eventstore.EventStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.confirmVerified
import io.mockk.mockk

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
                        val eventStreamId = mockk<EventStreamId>()

//                        coEvery {
//                            eventStreamId.get(eventStoreRepository = any())
//                        } returns
//                            EventStream(
//                                eventStreamId = eventStreamId,
//                                events = listOf(mockk<Event>()),
//                            )
//
//                        service.get(eventStreamId = eventStreamId)
//
//                        coVerify { eventStreamId.get(eventStoreRepository = eventStoreRepository) }
                        confirmVerified(eventStreamId)
                    }
                }
            }
        }
    })
