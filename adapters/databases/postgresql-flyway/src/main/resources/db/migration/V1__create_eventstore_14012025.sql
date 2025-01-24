CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE EventStore (
    eventId UUID PRIMARY KEY,
    metadata JSONB NOT NULL,
    occurredOn TIMESTAMP WITH TIME ZONE NOT NULL,
    owner VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    alias VARCHAR(255) NOT NULL,
    relatedIdentifiers JSONB NOT NULL,
    data JSONB NOT NULL,
    eventStreamId UUID NOT NULL,
    shouldSendToEventBus BOOLEAN NOT NULL,
    ownerId UUID NOT NULL
);

CREATE TABLE EventRelatedIdentifier (
    id UUID PRIMARY KEY,
    eventId UUID NOT NULL,
    relatedIdentifierId UUID NOT NULL
);
