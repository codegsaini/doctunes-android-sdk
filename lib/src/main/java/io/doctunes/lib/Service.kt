package io.doctunes.lib

import io.doctunes.lib.interfaces.Service
import io.ktor.client.HttpClient

class Service(
    private val client: HttpClient
) : Service {
    override suspend fun syncString() {

    }
}