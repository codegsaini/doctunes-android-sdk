package io.doctunes.lib

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object Module {

    private val client : HttpClient by lazy {
        HttpClient(Android) {
            expectSuccess = true
            install(ContentNegotiation) {
                json( Json { prettyPrint = true })
            }
        }
    }

    val repository : Repository by lazy {
        RepositoryImpl(client)
    }
}