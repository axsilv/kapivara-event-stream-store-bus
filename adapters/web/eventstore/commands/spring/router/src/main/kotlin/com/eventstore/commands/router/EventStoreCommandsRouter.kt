package com.eventstore.commands.router

import org.springframework.context.annotation.Bean

@Bean
fun eventStoreCommandsRouter(
    addEventHandler: AddEventHandler,
    addAllEventsHandler: AddAllEventsHandler,
): org.springframework.web.reactive.function.server.RouterFunction<*> =
    org.springframework.web.reactive.function.server.coRouter {
        POST("/event", addEventHandler::add)
        POST("/events", addAllEventsHandler::addAll)
    }
