package com.abapp.revestsolutions.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


actual fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {


        followRedirects = false


        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("KtorLog", message)
                }
            }
            level = LogLevel.ALL
        }

        install(HttpRedirect) {
            checkHttpMethod = false
        }


        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
            connectTimeoutMillis = 30_000
            socketTimeoutMillis = 60_000
        }
    }
}