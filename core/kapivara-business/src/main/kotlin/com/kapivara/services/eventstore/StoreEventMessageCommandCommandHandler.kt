package com.kapivara.services.eventstore

import com.kapivara.services.Command
import com.kapivara.services.CommandHandler

class StoreEventMessageCommandCommandHandler : CommandHandler<StoreEventMessageCommand> {

    override suspend fun store(command: StoreEventMessageCommand) {
        TODO("Not yet implemented")
    }
}