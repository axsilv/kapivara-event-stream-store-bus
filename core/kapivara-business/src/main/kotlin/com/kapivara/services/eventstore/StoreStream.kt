package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Event
import com.kapivara.services.CommandBusiness
import java.util.UUID

data class StoreStream(
    val id: UUID,
    val contextName: String?,
    val systemName: String?,
    val streamType: String?,
    val events: Set<Event>,
) : CommandBusiness
