package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.ports.EventStorageOptimizationRepository
import com.kapivara.domain.eventstore.ports.EventStorageRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class StoreStreamHandlerTest :
    BehaviorSpec({
        context("Store the stream to eventstore") {
            given("Single stream") {
                `when`("All context values") {
                    then("Should insert") {
                        val eventStorageRepository: EventStorageRepository = mockk()
                        val eventStorageOptimizationRepository: EventStorageOptimizationRepository = mockk()
                        val commandBusiness: StoreStream = mockk()
                        val stream: Stream = mockk()
                        val handler =
                            StoreStreamHandler(
                                eventStorageRepository,
                                eventStorageOptimizationRepository,
                            )

                        every {
                            commandBusiness.toStream()
                        } returns stream
                        coJustRun { stream.create(any(), any()) }

                        handler.store(commandBusiness)

                        verify { commandBusiness.toStream() }
                        coVerify { stream.create(eventStorageRepository, eventStorageOptimizationRepository) }
                        confirmVerified(commandBusiness, stream)
                    }
                }
            }
        }
    })
