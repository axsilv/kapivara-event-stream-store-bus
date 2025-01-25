package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.Config.databaseClient
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.R2dbcEventStoreRepositoryTestFixture.CLEAN_DB
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.R2dbcEventStoreRepositoryTestFixture.INSERT_EVENT_STREAM
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.R2dbcEventStoreRepositoryTestFixture.INSERT_IDENTITY
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.R2dbcEventStoreRepositoryTestFixture.INSERT_OWNER
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.R2dbcEventStoreRepositoryTestFixture.eventId
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.R2dbcEventStoreRepositoryTestFixture.eventStore
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.R2dbcEventStoreRepositoryTestFixture.eventStreamId
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.R2dbcEventStoreRepositoryTestFixture.ownerId
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.r2dbc.core.awaitRowsUpdated

class R2dbcEventStoreRepositoryTest :
    BehaviorSpec({

        val db = databaseClient()

        context("Test Event Store Repository Work Flow") {
            given("2 events") {
                `when`("Add those events and get by eventId + ownerId or eventStreamId") {
                    then("Should work with no exceptions") {
                        db.sql(INSERT_OWNER).fetch().awaitRowsUpdated()
                        db.sql(INSERT_EVENT_STREAM).fetch().awaitRowsUpdated()
                        db.sql(INSERT_IDENTITY).fetch().awaitRowsUpdated()

                        val repository = R2dbcEventStoreRepository(db)

                        repository.store(eventStore = eventStore)

                        val event1 = repository.get(eventId = eventId, ownerId = ownerId)!!

                        event1.eventId shouldBe eventStore.eventId
                        event1.metadata shouldBe eventStore.metadata
                        event1.owner shouldBe eventStore.owner
                        event1.type shouldBe eventStore.type
                        event1.alias shouldBe eventStore.alias
                        event1.relatedIdentifiers shouldBe eventStore.relatedIdentifiers
                        event1.data shouldBe eventStore.data
                        event1.eventStreamId shouldBe eventStore.eventStreamId
                        event1.shouldSendToEventBus shouldBe eventStore.shouldSendToEventBus
                        event1.ownerId shouldBe eventStore.ownerId
                        event1.identityId shouldBe eventStore.identityId

                        val event2 = repository.getStream(eventStreamId = eventStreamId).first()

                        event2.eventId shouldBe eventStore.eventId
                        event2.metadata shouldBe eventStore.metadata
                        event2.owner shouldBe eventStore.owner
                        event2.type shouldBe eventStore.type
                        event2.alias shouldBe eventStore.alias
                        event2.relatedIdentifiers shouldBe eventStore.relatedIdentifiers
                        event2.data shouldBe eventStore.data
                        event2.eventStreamId shouldBe eventStore.eventStreamId
                        event2.shouldSendToEventBus shouldBe eventStore.shouldSendToEventBus
                        event2.ownerId shouldBe eventStore.ownerId
                        event2.identityId shouldBe eventStore.identityId

                        db.sql(CLEAN_DB).fetch().awaitRowsUpdated()
                    }
                }
            }
        }
    })
