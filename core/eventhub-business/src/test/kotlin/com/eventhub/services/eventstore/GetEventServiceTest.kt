package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.toEventId
import com.eventhub.domain.eventstore.toOwnerId
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
                        val eventUuid = mockk<UUID>()
                        val eventId = mockk<EventId>()
                        val ownerUuid = mockk<UUID>()
                        val ownerId = mockk<Event.OwnerId>()
                        mockkStatic(UUID::toEventId)
                        mockkStatic(UUID::toOwnerId)

                        every { eventUuid.toEventId() } returns eventId
                        every { ownerUuid.toOwnerId() } returns ownerId
                        coEvery {
                            eventId.get(any(), any())
                        } returns event()

                        service.get(eventId = eventUuid, ownerId = ownerUuid)

                        verify { eventUuid.toEventId() }
                        verify { ownerUuid.toOwnerId() }
                        coVerify {
                            eventId.get(
                                eventStoreRepository = eventStoreRepository,
                                subscriberId = ownerId,
                            )
                        }
                        confirmVerified(eventStoreRepository, eventUuid, eventId)
                    }
                }
            }
        }
    })
