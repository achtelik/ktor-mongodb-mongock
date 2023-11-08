package it.achtelik

import io.ktor.server.application.*
import it.achtelik.basics.mongo.configureMongo
import it.achtelik.plugins.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
    configureMongo()
}
