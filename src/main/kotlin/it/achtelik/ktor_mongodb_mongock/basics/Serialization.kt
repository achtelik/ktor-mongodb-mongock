package it.achtelik.ktor_mongodb_mongock.basics

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureContentNegotiationAndSerialization() {
    install(ContentNegotiation) {
        json()
    }
}
