package io.doctunes.lib

import io.ktor.client.HttpClient

interface Repository {
    suspend fun syncStrings(callback: (String?, Exception?) -> Unit)
}

class RepositoryImpl(private val client: HttpClient): Repository {

    override suspend fun syncStrings(callback: (String?, Exception?) -> Unit) {
        try {
            callback(null, null)
        } catch (e: Exception) {
            callback("Error occurred", e)
        }
    }

}