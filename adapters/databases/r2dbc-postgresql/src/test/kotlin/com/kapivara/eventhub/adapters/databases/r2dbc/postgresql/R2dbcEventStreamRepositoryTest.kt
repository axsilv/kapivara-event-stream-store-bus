package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.eventhub.domain.eventstore.EventIdentity
import com.eventhub.domain.eventstore.EventMessage
import com.eventhub.domain.eventstore.EventPublisher
import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.Config.databaseClient
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Clock
import java.util.UUID.fromString
import java.util.UUID.randomUUID

class R2dbcEventStreamRepositoryTes :
    BehaviorSpec({
        context("Event stream repository test") {
            given("1 stream and 50 messages") {
                then("Should insert the stream and all the messages then retrieves it") {
                    val eventStreamId = EventStreamId(randomUUID())
                    val eventStream =
                        EventStream(
                            id = eventStreamId,
                            eventMessages = emptyList(),
                            streamExternalReference = fromString("18eb47f6-a73b-4f05-8657-96c01feaf3ab"),
                            streamExternalReferenceHash = "18eb47f6-a73b-4f05-8657-96c01feaf3ab".hashCode().toLong(),
                            createdAt = Clock.System.now(),
                        )
                    val messages =
                        (1..50).map {
                            EventMessage(
                                id = EventMessage.EventMessageId(randomUUID()),
                                identityId = EventIdentity.IdentityId(1L),
                                publisherId = EventPublisher.PublisherId(2L),
                                eventStreamId = eventStreamId,
                                payload = "payload",
                                position = 1L,
                                isFinal = false,
                                occurredOn = Clock.System.now(),
                            )
                        }
                    val repository = R2dbcEventStreamRepository(databaseClient())

                    repository.store(eventStream)

                    messages.forEach {
                        repository.store(it)
                    }

                    val stream = repository.fetch(eventStreamId)

                    stream.shouldNotBeNull()
                    stream.eventMessages.size shouldBe messages.size
                }
            }
        }
    })
