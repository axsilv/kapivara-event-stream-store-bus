package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

object Queries {
    val INSERT_EVENT_STORE =
        """
        INSERT INTO EventStore (
            eventId, 
            metadata, 
            occurredOn, 
            owner, 
            type, 
            alias, 
            relatedIdentifiers,
             data, 
             eventStreamId, 
             shouldSendToEventBus, 
             ownerId
        ) VALUES (
            :eventId, 
            :metadata, 
            :occurredOn, 
            :owner, 
            :type, 
            :alias, 
            :relatedIdentifiers, 
            :data,
            :eventStreamId, 
            :shouldSendToEventBus, 
            :ownerId
        )
        """.trimIndent()

    val INSERT_EVENT_RELATED_IDENTIFIER =
        """
        INSERT INTO EventRelatedIdentifier (
            id, 
            eventId, 
            relatedIdentifierId
        ) VALUES (
            :id, 
            :eventId, 
            :relatedIdentifierId
        )
        """.trimIndent()

    val SELECT_BY_EVENT_STREAM_ID =
        """
        SELECT 
            eventId, 
            metadata, 
            occurredOn, 
            owner, 
            type, 
            alias, 
            relatedIdentifiers,
             data, 
             eventStreamId, 
             shouldSendToEventBus, 
             ownerId
        FROM EventStore 
        WHERE eventStreamId = :eventStreamId;
        """.trimIndent()

    val SELECT_BY_EVENT_ID_AND_OWNER_ID =
        """
        SELECT
            eventId, 
            metadata, 
            occurredOn, 
            owner, 
            type, 
            alias, 
            relatedIdentifiers,
             data, 
             eventStreamId, 
             shouldSendToEventBus, 
             ownerId
        FROM EventStore 
        WHERE eventId = :eventId 
        AND ownerId = :ownerId;
        """.trimIndent()
}
