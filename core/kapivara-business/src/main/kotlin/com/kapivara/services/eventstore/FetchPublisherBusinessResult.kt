package com.kapivara.services.eventstore

import com.kapivara.services.QueryBusinessResult

data class FetchPublisherBusinessResult(
    val id: Long,
    val publisherName: String,
) : QueryBusinessResult
