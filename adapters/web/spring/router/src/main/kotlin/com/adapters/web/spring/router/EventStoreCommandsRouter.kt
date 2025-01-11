package com.adapters.web.spring.router

import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.coRouter

fun eventStoreCommandsRouter(
    addEventHandler: AddEventHandler,
    addAllEventsHandler: AddAllEventsHandler,
): RouterFunction<*> =
    coRouter {
        POST("/event", addEventHandler::add)
        POST("/events", addAllEventsHandler::addAll)
    }
