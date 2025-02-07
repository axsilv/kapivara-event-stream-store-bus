package com.kapivara.services.eventstore

import com.kapivara.services.CommandBusinessHandler

class StoreSubscriberBusinessHandler : CommandBusinessHandler<StoreSubscriberBusiness> {
    override suspend fun store(commandBusiness: StoreSubscriberBusiness) {
        TODO("Not yet implemented")
    }
}
