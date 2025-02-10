package com.kapivara.services

fun interface QueryBusinessHandler<T : QueryBusiness, R : QueryBusinessResult> {
    suspend fun fetch(queryBusiness: T): R?
}
