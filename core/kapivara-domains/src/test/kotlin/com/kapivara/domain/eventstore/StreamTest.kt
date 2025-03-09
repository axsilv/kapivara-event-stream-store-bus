package com.kapivara.domain.eventstore

import com.kapivara.domain.eventstore.ports.EventStorageOptimizationRepository
import com.kapivara.domain.eventstore.ports.EventStorageRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifyAll
import io.mockk.confirmVerified
import io.mockk.mockk
import java.util.UUID.randomUUID
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class StreamTest :
    BehaviorSpec({
        context("Test stream domain cases") {
            given("1 stream to create") {
                `when`("Calls create function") {
                    then("Should create stream") {
                        val eventStorageRepository = mockk<EventStorageRepository>()
                        val eventStorageOptimizationRepository = mockk<EventStorageOptimizationRepository>()
                        val stream =
                            Stream(
                                id = randomUUID().toStreamId(),
                                contextName = "contextName",
                                systemName = "systemName",
                                streamType = "streamType",
                                events = linkedSetOf(),
                                createdAt = now().toEpochMilliseconds(),
                            )

                        coJustRun {
                            eventStorageRepository.append(any())
                        }
                        coJustRun {
                            eventStorageOptimizationRepository.store(any())
                        }

                        stream.create(
                            eventStorageRepository,
                            eventStorageOptimizationRepository,
                        )

                        coVerifyAll {
                            eventStorageRepository.append(stream)
                            eventStorageOptimizationRepository.store(stream.arrangement())
                        }
                        confirmVerified(
                            eventStorageRepository,
                            eventStorageOptimizationRepository,
                        )
                    }
                }

                `when`("Calls fetch function") {
                    val eventStorageRepository = mockk<EventStorageRepository>()
                    val id = randomUUID().toStreamId()
                    val stream =
                        Stream(
                            id = id,
                            contextName = "contextName",
                            systemName = "systemName",
                            streamType = "streamType",
                            events = linkedSetOf(),
                            createdAt = now().toEpochMilliseconds(),
                        )

                    coEvery { eventStorageRepository.fetchLast(any()) } returns stream

                    Stream.fetch(
                        id = id,
                        contextName = "contextName",
                        systemName = "systemName",
                        streamType = "streamType",
                        eventStorageRepository = eventStorageRepository,
                    )

                    coVerify {
                        eventStorageRepository.fetchLast(
                            linkedSetOf(
                                "contextName",
                                "systemName",
                                "streamType",
                                id.value.toString(),
                            ),
                        )
                    }
                    confirmVerified(
                        eventStorageRepository,
                    )
                }
            }
        }
    })
