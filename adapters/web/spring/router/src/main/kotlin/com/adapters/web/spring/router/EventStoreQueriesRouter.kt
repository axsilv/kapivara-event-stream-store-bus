package com.adapters.web.spring.router

import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.coRouter

fun eventStoreQueriesRouter(
    getEventHandler: GetEventHandler,
    getEventStreamHandler: GetEventStreamHandler,
): RouterFunction<*> =
    coRouter {
        GET("/event", getEventHandler::get)
        GET("/event/stream", getEventStreamHandler::get)
    }
