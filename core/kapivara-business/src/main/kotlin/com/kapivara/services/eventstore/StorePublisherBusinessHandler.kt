package com.kapivara.services.eventstore

import com.kapivara.services.CommandBusinessHandler

class StorePublisherBusinessHandler : CommandBusinessHandler<StorePublisherBusiness> {
    override suspend fun store(commandBusiness: StorePublisherBusiness) {
        TODO("Not yet implemented")
    }
}
