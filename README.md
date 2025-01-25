# Kapivara EventHub

## Overview

Kapivara EventHub is a service developed as part of personal studies to explore advanced software architecture concepts and techniques. It focuses on event-driven systems, scalability, and reliability.

## Key Features

- **Event Management**: The system is capable of receiving and processing events from multiple sources efficiently.
- **Event Storage**: Events are persisted in a specialized event-oriented database designed for high performance.
- **Consistency and Reliability**: Ensures data consistency and implements robust mechanisms to handle failures effectively.
- **Scalability**: Supports seamless scaling to accommodate increased event loads.

## Core Technologies

- **Programming Language**: Kotlin
- **Framework**: Spring
- **Database**: PostgreSQL

## Architectural Patterns and Design Principles

- **Hexagonal Architecture**: Ensures separation of concerns by isolating the core business logic from external dependencies.
- **CQRS (Command Query Responsibility Segregation)**: Separates read and write operations for better performance and scalability.
- **Asynchronous and Reactive Systems**: Leverages non-blocking operations for improved responsiveness.
- **Non-Blocking Operations**: Ensures system resources are utilized optimally.
- **Append-Only Database Operations**: Adopts an immutable data storage approach.
- **Idempotent Event Handling**: Ensures that events can be delivered multiple times without adverse effects on the system state.

## Event Store

- The event store is intentionally designed to be minimalistic, supporting only append-only operations for commands and straightforward query mechanisms for searches.

## Event Bus

- **Bucket-Based Organization**: Events are distributed into multiple buckets, with each consumer assigned specific buckets.
- **Deterministic Assignment**: Events for a specific consumer are always routed to the same bucket.
- **Commit Mechanism**: Consumers must commit after retrieving events to ensure reliable processing.
- **Data Retrieval**: Consumers can retrieve events using various strategies:
    - All events in a specific bucket.
    - The last committed event in a bucket.
    - Events associated with a specific stream.
    - All events across the consumer’s assigned buckets.
- **Single Application Node per Bucket**: Each bucket is exclusively assigned to one application node to prevent conflicts.
- **Subscription Model**: Consumers must subscribe to buckets using a unique key.

