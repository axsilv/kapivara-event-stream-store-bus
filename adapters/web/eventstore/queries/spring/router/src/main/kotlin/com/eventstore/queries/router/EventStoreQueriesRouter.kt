package com.eventstore.queries.router

import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.coRouter

@Bean
fun eventStoreQueriesRouter(
    getEventHandler: GetEventHandler,
    getEventStreamHandler: GetEventStreamHandler,
): RouterFunction<*> =
    coRouter {
        GET("/event", getEventHandler::get)
        GET("/event/stream", getEventStreamHandler::get)
    }
