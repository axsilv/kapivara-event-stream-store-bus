package com.eventhub.services.eventstore

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
                        val eventBusRepository = mockk<EventBusRepository>()
                        val service =
                            AddEventService(
                                eventStoreRepository = eventStoreRepository,
                                eventBusRepository = eventBusRepository,
                            )
                        val event = mockk<Event>()
                        val addEvent = mockk<AddEvent>()
                        mockkStatic(AddEvent::toEvent)

                        every {
                            addEvent.toEvent()
                        } returns event

                        coJustRun {
                            event.store(
                                eventStoreRepository = eventStoreRepository,
                                eventBusRepository = eventBusRepository,
                            )
                        }

                        service.add(addEvent = addEvent)

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
