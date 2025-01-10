package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.ports.eventbus.EventBusClient
import com.eventhub.ports.eventstore.EventStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk

class AddEventServiceTest :
    BehaviorSpec({

        context("Add a new event") {
            given("A new event") {
                `when`("CallS event add function") {
                    then("Should just run") {
                        val eventStoreRepository = mockk<EventStoreRepository>()
                        val eventBusClient = mockk<EventBusClient>()
                        val service =
                            AddEventService(
                                eventStoreRepository = eventStoreRepository,
                                eventBusClient = eventBusClient,
                            )
                        val event = mockk<Event>(relaxed = true)

                        coJustRun {
                            event.add(
                                eventStoreRepository = eventStoreRepository,
                                eventBusClient = eventBusClient,
                            )
                        }

                        service.add(event = event)

                        coVerify {
                            event.add(
                                eventStoreRepository = eventStoreRepository,
                                eventBusClient = eventBusClient,
                            )
                        }
                        confirmVerified(event)
                    }
                }
            }
        }
    })
