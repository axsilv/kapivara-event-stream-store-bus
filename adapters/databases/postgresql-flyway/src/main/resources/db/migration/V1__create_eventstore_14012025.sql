CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE event_streams (
    id UUID PRIMARY KEY,
    messages JSONB NOT NULL,
    stream_external_reference UUID NOT NULL,
    stream_external_reference_hash BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE identities (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    publisher_id UUID NOT NULL,
    subscribers_id JSONB NOT NULL,
    metadata JSONB NOT NULL
);

CREATE TABLE mutexes (
    lock VARCHAR(255) PRIMARY KEY,
    expires_at TIMESTAMPTZ NOT NULL
);
