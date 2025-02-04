package com.kapivara.adapters.spring.file.database

object FileDatabaseVariables {
    fun dataPath(): String = System.getenv("FILE_PATH") ?: "${System.getProperty("user.home")}/kapivara/data"

    fun streamPath() = dataPath() + "/eventstore/streams"

    fun bucketsPath() = dataPath() + "/eventbus/buckets"

    fun hidePath() = dataPath() + "/eventbus/buckets/hide"

    fun identitiesPath() = dataPath() + "/eventstore/identities"

    fun publishersPath() = dataPath() + "/eventstore/publishers"

    fun subscribersPath() = dataPath() + "/eventbus/subscribers"
}
