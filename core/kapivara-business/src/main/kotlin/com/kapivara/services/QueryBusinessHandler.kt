package com.kapivara.services

fun interface QueryBusinessHandler<T : QueryBusiness> {
    suspend fun fetch(queryBusiness: T)
}
