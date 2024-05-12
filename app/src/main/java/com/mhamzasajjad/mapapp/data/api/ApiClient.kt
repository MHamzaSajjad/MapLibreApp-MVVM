package com.mhamzasajjad.mapapp.data.api


import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class ApiClient() {
    @OptIn(ExperimentalSerializationApi::class)
    val client = HttpClient(OkHttp) {
        install(HttpTimeout) {
            requestTimeoutMillis = 2000000
            connectTimeoutMillis = 2000000
            socketTimeoutMillis = 2000000
        }

        install(ContentNegotiation) {
            json(json = Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = true
            }
            )
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            url {
                host = ApiEndPoints.userProfileMockBaseUrl
                protocol = URLProtocol.HTTPS
            }
            contentType(ContentType.Application.Json)
            header("Accept", "application/json")
        }
    }

}
