package com.kapivara.services.eventstore

import com.kapivara.services.CommandBusinessHandler

class StoreEventStreamBusinessHandler : CommandBusinessHandler<StoreEventStreamBusiness> {
    override suspend fun store(commandBusiness: StoreEventStreamBusiness) {
        TODO("Not yet implemented")
    }
}
