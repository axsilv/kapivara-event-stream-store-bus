package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

object Queries {
    const val INSERT_EVENT_STORE =
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
             ownerId,
             identityId
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
            :ownerId,
            :identityId
        )
        """

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
        """

    val INSERT_OWNER =
        """
        INSERT INTO Owner (
            ownerId, 
            name
        ) VALUES (
            :ownerId, 
            :ownerId
        )
        """

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
             ownerId,
             identityId
        FROM EventStore 
        WHERE eventStreamId = :eventStreamId;
        """

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
             ownerId,
             identityId
        FROM EventStore 
        WHERE eventId = :eventId 
        AND ownerId = :ownerId;
        """
}
