# Kapivara EventHub

## Overview

Kapivara Event Streaming Aggregation is a service developed as part of personal studies to explore advanced software architecture concepts and techniques. It focuses on event-driven systems, scalability, and reliability.

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

## Event Aggregate Store

- The event store is intentionally designed to be minimalistic, supporting only append-only operations for commands and straightforward query mechanisms for searches.
- 

## Event Aggregate Bus

- **Bucket-Based Organization**: Event's aggregate are distributed into multiple buckets.
- **Deterministic Assignment**: Each subscriber has it's bucket and event's aggregate are routed to it, no matter the identity.
- **Commit Mechanism**: Subscribers must commit after retrieving events to ensure reliable processing.
- **Streaming Aggregation**: aggregated events are collect to a stream to be delivery after the finish position arives

