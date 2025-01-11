package com.eventhub.services.eventstore

import com.eventhub.ports.eventbus.EventBusClient
import com.eventhub.ports.eventstore.EventStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.confirmVerified
import io.mockk.mockk

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
                        val event = mockk<AddEvent>(relaxed = true)

//                        coJustRun {
//                            event.add(
//                                eventStoreRepository = eventStoreRepository,
//                                eventBusClient = eventBusClient,
//                            )
//                        }

                        service.addAll(addEvents = listOf(event))

//                        coVerify {
//                            event.add(
//                                eventStoreRepository = eventStoreRepository,
//                                eventBusClient = eventBusClient,
//                            )
//                        }
                        confirmVerified(event)
                    }
                }
            }
        }
    })
