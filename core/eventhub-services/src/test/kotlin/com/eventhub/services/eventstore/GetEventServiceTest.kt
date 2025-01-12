package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.toEventId
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

class GetEventServiceTest :
    BehaviorSpec({
        context("Get a single event") {
            given("Event id") {
                `when`("Calls get event") {
                    then("Return the event") {
                        val eventStoreRepository = mockk<EventStoreRepository>()
                        val service =
                            GetEventService(
                                eventStoreRepository = eventStoreRepository,
                            )
                        val event = mockk<Event>()
                        val eventUuid = mockk<UUID>()
                        val eventId = mockk<EventId>()
                        mockkStatic(UUID::toEventId)

                        every { eventUuid.toEventId() } returns eventId
                        coEvery {
                            eventId.get(any())
                        } returns event

                        service.get(eventId = eventUuid)

                        verify { eventUuid.toEventId() }
                        coVerify { eventId.get(eventStoreRepository = eventStoreRepository) }
                        confirmVerified(eventStoreRepository, eventUuid, eventId)
                    }
                }
            }
        }
    })
