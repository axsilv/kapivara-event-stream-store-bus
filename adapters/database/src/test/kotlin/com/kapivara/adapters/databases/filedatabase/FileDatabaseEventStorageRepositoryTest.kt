package com.kapivara.adapters.databases.filedatabase

import com.kapivara.domain.eventstore.Message
import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.toMessageId
import com.kapivara.domain.eventstore.toStreamId
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.datetime.Clock.System.now
import java.util.UUID.randomUUID
import kotlin.io.path.Path

class FileDatabaseEventStorageRepositoryTest :
    BehaviorSpec({
        context("Must test file database contract") {
            given("One stream with one message") {
                `when`("Execute all contract functions") {
                    then("Should work") {
                        System.setProperty(
                            "FILE_PATH",
                            Path("src", "test", "resources", "kapivara", "data").toString(),
                        )
                        val database = FileDatabase()
                        val streamId = randomUUID().toStreamId()
                        val streamV0 =
                            Stream(
                                id = streamId,
                                contextName = "contextName",
                                systemName = "systemName",
                                streamType = "streamType",
                                eventMessages =
                                    persistentSetOf(
                                        Message(
                                            id = randomUUID().toMessageId(),
                                            messageName = "messageName",
                                            payloadFormat = "json",
                                            payloadType = "payloadType",
                                            payload = "payload",
                                            position = 1,
                                            isFinal = true,
                                            occurredOn = now(),
                                        ),
                                    ),
                                createdAt = now().toEpochMilliseconds(),
                            )

                        with(FileDatabaseEventStorageRepository(database)) {
                            append(streamV0)
                            val result = fetch(streamV0.arrangement())!!
                            result.id.value shouldBe streamV0.id.value
                            result.contextName shouldBe streamV0.contextName
                            result.systemName shouldBe streamV0.systemName
                            result.streamType shouldBe streamV0.streamType
                            result.createdAt shouldBe streamV0.createdAt
                        }
                    }
                }
            }
        }
    })
