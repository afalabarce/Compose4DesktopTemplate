package io.github.afalabarce.compose.desktop.datasource.remote.engines

import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

fun provideKtorClientFactory(): HttpClient = HttpClient(Java){
    install(ContentNegotiation){
        gson {
            setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        }
    }
    engine {
        config {
            followRedirects(java.net.http.HttpClient.Redirect.ALWAYS)
        }

        threadsCount = 8
        pipelining = true
    }
}