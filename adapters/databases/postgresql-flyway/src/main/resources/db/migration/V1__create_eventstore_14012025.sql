CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Owner (
    ownerId UUID PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE EventStream (
    eventStreamId UUID PRIMARY KEY,
    ownerId UUID,
    FOREIGN KEY (ownerId) REFERENCES Owner(ownerId)
);

CREATE TABLE Identity (
    identityId UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
);

CREATE TABLE EventStore (
    eventId UUID PRIMARY KEY,
    eventStreamId UUID,
    ownerId UUID,
    identityId UUID,
    metadata VARCHAR NOT NULL,
    occurredOn TIMESTAMP WITH TIME ZONE NOT NULL,
    owner VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    alias VARCHAR(255) NOT NULL,
    relatedIdentifiers VARCHAR NOT NULL,
    data VARCHAR NOT NULL,
    shouldSendToEventBus BOOLEAN NOT NULL,
    FOREIGN KEY (eventStreamId) REFERENCES EventStream(eventStreamId),
    FOREIGN KEY (ownerId) REFERENCES Owner(ownerId),
    FOREIGN KEY (identityId) REFERENCES Identity(identityId)
);

CREATE TABLE EventRelatedIdentifier (
    id UUID PRIMARY KEY,
    eventId UUID NOT NULL,
    relatedIdentifierId UUID NOT NULL
);
