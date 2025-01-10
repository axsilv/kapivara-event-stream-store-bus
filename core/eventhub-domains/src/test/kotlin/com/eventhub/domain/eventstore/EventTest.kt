package com.eventhub.domain.eventstore

import com.eventhub.domain.eventstore.EventTestFixture.event
import com.eventhub.domain.eventstore.EventTestFixture.eventStore
import com.eventhub.domain.eventstore.EventTestFixture.eventUuid
import com.eventhub.ports.eventbus.EventBusClient
import com.eventhub.ports.eventstore.EventStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk

class EventTest :
    BehaviorSpec({

        context("Event domain behavior") {
            given("A single event") {
                `when`("Add a new event without event bus") {
                    then("Should call event repository at add fun") {
                        val event = event(shouldSendToEventBus = false)
                        val eventStoreRepository = mockk<EventStoreRepository>()

                        coJustRun { eventStoreRepository.add(any()) }

                        event.add(
                            eventStoreRepository = eventStoreRepository,
                            eventBusClient = mockk(),
                        )

                        coVerify { eventStoreRepository.add(any()) }
                        confirmVerified(eventStoreRepository)
                    }
                }

                `when`("Add a new event without event bus") {
                    then("Should call event repository at add fun") {
                        val event = event(shouldSendToEventBus = true)
                        val eventStoreRepository = mockk<EventStoreRepository>()
                        val eventBusClient = mockk<EventBusClient>()

                        coJustRun { eventStoreRepository.add(any()) }
                        coJustRun { eventBusClient.send().await() }

                        event.add(
                            eventStoreRepository = eventStoreRepository,
                            eventBusClient = eventBusClient,
                        )

                        coVerify { eventStoreRepository.add(any()) }
                        coVerify { eventBusClient.send().await() }
                        confirmVerified(eventStoreRepository, eventBusClient)
                    }
                }

                `when`("Get a event from event store repository") {
                    then("Should return the event") {
                        val eventStoreRepository = mockk<EventStoreRepository>()

                        coEvery { eventStoreRepository.get(any()) } returns eventStore()

                        eventUuid
                            .toEventId()
                            .get(
                                eventStoreRepository = eventStoreRepository,
                            ) shouldBe event(shouldSendToEventBus = true)

                        coVerify { eventStoreRepository.get(any()) }
                        confirmVerified(eventStoreRepository)
                    }
                }
            }
        }
    })
