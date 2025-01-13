package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.toEvent
import com.eventhub.ports.eventbus.EventBusClient
import com.eventhub.ports.eventstore.AddEvent
import com.eventhub.ports.eventstore.EventStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify

class AddEventServiceTest :
    BehaviorSpec({

        context("Add a new event") {
            given("A new event") {
                `when`("CallS event add function") {
                    then("Just run") {
                        val eventStoreRepository = mockk<EventStoreRepository>()
                        val eventBusClient = mockk<EventBusClient>()
                        val service =
                            AddEventService(
                                eventStoreRepository = eventStoreRepository,
                                eventBusClient = eventBusClient,
                            )
                        val event = mockk<Event>()
                        val addEvent = mockk<AddEvent>()
                        mockkStatic(AddEvent::toEvent)

                        every {
                            addEvent.toEvent()
                        } returns event

                        coJustRun {
                            event.add(
                                eventStoreRepository = eventStoreRepository,
                                eventBusClient = eventBusClient,
                            )
                        }

                        service.add(addEvent = addEvent)

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
