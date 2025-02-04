package com.kapivara.adapters.file.database

object FileDatabaseVariables {
    fun dataPath(): String = System.getenv("FILE_PATH") ?: "${System.getProperty("user.home")}/kapivara/data"

    fun streamPath() = dataPath() + "/streams"

    fun bucketsPath() = dataPath() + "/buckets"

    fun hidePath() = dataPath() + "/buckets/hide"

    fun identitiesPath() = dataPath() + "/identities"

    fun publishersPath() = dataPath() + "/publishers"

    fun subscribersPath() = dataPath() + "/subscribers"
}
