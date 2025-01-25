package com.eventhub.domain.eventstore

import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.EventStreamTestFixture.eventStream
import com.eventhub.domain.eventstore.EventTestFixture.eventStore
import com.eventhub.domain.eventstore.EventTestFixture.eventStreamUuid
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk

class EventStreamTest :
    BehaviorSpec({

        context("Event stream") {
            given("Two events for the same stream") {
                `when`("Tries to find this stream") {
                    then("Should retrieve stream") {
                        val eventStoreRepository = mockk<EventStoreRepository>()

                        coEvery { eventStoreRepository.getStream(any()) } returns
                            listOf(
                                eventStore(),
                                eventStore(),
                            )

                        EventStreamId(eventStreamUuid).get(
                            eventStoreRepository = eventStoreRepository,
                        ) shouldBe eventStream()

                        coVerify { eventStoreRepository.getStream(any()) }
                        confirmVerified(eventStoreRepository)
                    }
                }
            }
        }
    })
