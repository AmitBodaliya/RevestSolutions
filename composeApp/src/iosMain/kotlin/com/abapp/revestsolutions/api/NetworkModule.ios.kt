package com.abapp.revestsolutions.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.*


actual fun createHttpClient(): HttpClient {
    return HttpClient(Darwin) {


        install(Logging) {
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                    encodeDefaults = true
                    coerceInputValues = true
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            socketTimeoutMillis = 30_000
        }

        engine {
            configureRequest {
                setAllowsCellularAccess(true)
                setTimeoutInterval(30.0)
            }
        }
    }
}
