# Kapivara EventHub

## Description

This service was developed for my personal studies.

## Features

- **Event Management**: Receives and processes events from multiple sources.
- **Event Storage**: Persists events in an event-oriented database.
- **Consistency and Reliability**: Maintains data consistency and handles failures efficiently.
- **Scalability**: Supports an increase in the number of events.

## Technologies Used

- Kotlin 
- Spring 
- Postgresql 

## Architecture and Design Used

- Hexagonal Architecture
- CQRS (Command Query Responsibility Segregation)
- Asynchronous and Reactive Systems
- Non-Blocking Operations

## Event Store

- The event store should be as simple as possible, featuring only append-only operations for commands and straightforward query operations for searches.
- When an event is added to the event store, it may or may not be propagated to the event bus, depending on the decision made by the event attribute shouldSendToEventBus Boolean.

## Event Bus
- Must notify the message and the client choose when or not to retrieve it