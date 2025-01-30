# Kapivara Event Stream Store Bus

## Overview

Kapivara Event Streaming Aggregation is a service developed as part of personal studies to explore advanced software architecture concepts and techniques. It focuses on event-driven systems, scalability, and reliability.

An Event Stream Store Bus is a combination of two concepts: an Event Bus and an Event Store. Here's a brief overview of each:

**Event Bus**

An Event Bus is a mechanism that dispatches events to subscribed event handlers. It acts as a central hub where events are published and then routed to the appropriate consumers or listeners1. This allows for decoupling of event producers and consumers, promoting a more modular and scalable architecture.

**Event Store**

An Event Store is a storage system that persists events. It records events as they occur and allows for replaying or querying past events based on certain criteria, such as an aggregate identifier1. This is particularly useful for event sourcing, where the state of an application is determined by the sequence of events that have occurred.

Combining Both
When you combine an Event Bus with an Event Store, you get a system where events are not only routed to the appropriate handlers but also stored for future reference. This setup is beneficial for applications that need to maintain a history of events for auditing, replaying past states, or debugging purposes1.

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

