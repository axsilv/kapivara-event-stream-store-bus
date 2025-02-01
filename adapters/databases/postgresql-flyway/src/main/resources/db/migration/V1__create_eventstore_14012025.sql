CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE event_streams (
    id UUID PRIMARY KEY,
    messages JSONB NOT NULL,
    stream_external_reference UUID NOT NULL,
    stream_external_reference_hash BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);
