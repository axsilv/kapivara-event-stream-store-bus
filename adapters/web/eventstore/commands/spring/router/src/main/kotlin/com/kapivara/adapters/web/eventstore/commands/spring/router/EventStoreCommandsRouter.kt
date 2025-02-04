package com.kapivara.adapters.web.eventstore.commands.spring.router

import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.coRouter

@Bean
fun eventStoreCommandsRouter(
    addEventHandler: AddEventHandler,
    addAllEventsHandler: AddAllEventsHandler,
): RouterFunction<*> =
    coRouter {
        POST("/event", addEventHandler::add)
        POST("/events", addAllEventsHandler::addAll)
    }
