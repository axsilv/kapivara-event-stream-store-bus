package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify

class AddAllEventsServiceTest :
    BehaviorSpec({

        context("Add multiple events") {
            given("List of events") {
                `when`("Calls add all") {
                    then("Just run") {
                        val eventStoreRepository = mockk<EventStoreRepository>()
                        val eventBusRepository = mockk<EventBusRepository>()
                        val service =
                            AddAllEventsService(
                                eventStoreRepository = eventStoreRepository,
                                eventBusRepository = eventBusRepository,
                            )
                        val event = mockk<Event>()
                        val addEvent = mockk<AddEvent>()
                        mockkStatic(AddEvent::toEvent)

                        every { addEvent.toEvent() } returns event
                        coJustRun {
                            event.store(
                                eventStoreRepository = any(),
                                eventBusRepository = any(),
                            )
                        }

                        service.addAll(addEvents = listOf(addEvent))

                        verify { addEvent.toEvent() }
                        coVerify {
                            event.store(
                                eventStoreRepository = eventStoreRepository,
                                eventBusRepository = eventBusRepository,
                            )
                        }
                        confirmVerified(event, addEvent)
                    }
                }
            }
        }
    })
