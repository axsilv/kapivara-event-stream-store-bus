package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.eventhub.ports.eventstore.EventStore
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import java.util.UUID
import java.util.UUID.fromString

object R2dbcEventStoreRepositoryTestFixture {
    val eventId: UUID = fromString("292efaf7-b430-4ea7-91cb-d70fc3ed6391")
    val eventStreamId: UUID = fromString("6570851c-2799-4ac3-b708-02e19045e717")
    val ownerId: UUID = fromString("b14de107-78a3-49b6-9a46-7ebb23fa3f5d")
    val identityId: UUID = fromString("1ab2dee2-1f87-4861-b685-c57a80e29707")
    val data: String = Json.encodeToString(mapOf("data" to "test"))

    val eventStore: EventStore =
        EventStore(
            eventId = eventId,
            metadata = mapOf(),
            occurredOn = Clock.System.now(),
            owner = "owner_test",
            type = "type_test",
            alias = "alias_test",
            relatedIdentifiers = mapOf(),
            data = data,
            eventStreamId = eventStreamId,
            shouldSendToEventBus = true,
            ownerId = ownerId,
            identityId = identityId,
        )

    const val INSERT_OWNER = """
    INSERT INTO Owner (ownerId, name) 
    VALUES ('b14de107-78a3-49b6-9a46-7ebb23fa3f5d', 'owner_test');
"""

    const val INSERT_EVENT_STREAM = """
    INSERT INTO EventStream (ownerId, eventStreamId) 
    VALUES ('b14de107-78a3-49b6-9a46-7ebb23fa3f5d', '6570851c-2799-4ac3-b708-02e19045e717');
"""

    const val INSERT_IDENTITY = """
    INSERT INTO Identity (ownerId, identityId) 
    VALUES ('b14de107-78a3-49b6-9a46-7ebb23fa3f5d', '1ab2dee2-1f87-4861-b685-c57a80e29707');
"""

    const val CLEAN_DB = """
    DELETE FROM EventStore WHERE eventId = '292efaf7-b430-4ea7-91cb-d70fc3ed6391';
    DELETE FROM Identity WHERE identityId = '1ab2dee2-1f87-4861-b685-c57a80e29707';
    DELETE FROM EventStreamByOwner WHERE eventStreamId = '6570851c-2799-4ac3-b708-02e19045e717';
    DELETE FROM Owner WHERE ownerId = 'b14de107-78a3-49b6-9a46-7ebb23fa3f5d';
"""
}
