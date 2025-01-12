package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.ports.eventbus.EventBusClient
import com.eventhub.ports.eventstore.EventStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AddAllEventsServiceTest :
    BehaviorSpec({

        context("Add multiple events") {
            given("List of events") {
                `when`("Calls add all") {
                    then("Just run") {
                        val eventStoreRepository = mockk<EventStoreRepository>()
                        val eventBusClient = mockk<EventBusClient>()
                        val service =
                            AddAllEventsService(
                                eventStoreRepository = eventStoreRepository,
                                eventBusClient = eventBusClient,
                            )
                        val event = mockk<Event>()
                        val addEvent = mockk<AddEvent>()

                        every { addEvent.toEvent() } returns event
                        coJustRun {
                            event.add(
                                eventStoreRepository = any(),
                                eventBusClient = any(),
                            )
                        }

                        service.addAll(addEvents = listOf(addEvent))

                        verify { addEvent.toEvent() }
                        coVerify {
                            event.add(
                                eventStoreRepository = eventStoreRepository,
                                eventBusClient = eventBusClient,
                            )
                        }
                        confirmVerified(event, addEvent)
                    }
                }
            }
        }
    })
