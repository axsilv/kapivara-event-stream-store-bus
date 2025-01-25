package com.eventhub.domain.eventstore

import com.eventhub.domain.eventstore.EventTestFixture.event
import com.eventhub.domain.eventstore.EventTestFixture.eventStore
import com.eventhub.domain.eventstore.EventTestFixture.eventUuid
import com.eventhub.domain.eventstore.EventTestFixture.ownerId
import com.eventhub.domain.eventstore.ports.EventStoreRepository
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

                        coJustRun { eventStoreRepository.store(any()) }

                        event.store(
                            eventStoreRepository = eventStoreRepository,
                            eventBusRepository = mockk(),
                        )

                        coVerify { eventStoreRepository.store(any()) }
                        confirmVerified(eventStoreRepository)
                    }
                }

                `when`("Add a new event without event bus") {
                    then("Should call event repository at add fun") {
                        val event = event(shouldSendToEventBus = true)
                        val eventStoreRepository = mockk<EventStoreRepository>()
                        val eventBusRepository = mockk<EventBusRepository>()

                        coJustRun { eventStoreRepository.store(any()) }
                        coJustRun { eventBusRepository.send().await() }

                        event.store(
                            eventStoreRepository = eventStoreRepository,
                            eventBusRepository = eventBusRepository,
                        )

                        coVerify { eventStoreRepository.store(any()) }
                        coVerify { eventBusRepository.send().await() }
                        confirmVerified(eventStoreRepository, eventBusRepository)
                    }
                }

                `when`("Get a event from event store repository") {
                    then("Should return the event") {
                        val eventStoreRepository = mockk<EventStoreRepository>()

                        coEvery { eventStoreRepository.fetch(any(), any()) } returns eventStore()

                        eventUuid
                            .toEventId()
                            .get(
                                eventStoreRepository = eventStoreRepository,
                                ownerId = Event.OwnerId(ownerId),
                            ) shouldBe event(shouldSendToEventBus = true)

                        coVerify { eventStoreRepository.fetch(any(), any()) }
                        confirmVerified(eventStoreRepository)
                    }
                }
            }
        }
    })
