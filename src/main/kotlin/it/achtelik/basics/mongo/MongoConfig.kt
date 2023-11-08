package it.achtelik.basics.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase
import io.ktor.server.application.*
import org.bson.UuidRepresentation

object MongoDbConfig {
    private lateinit var client: MongoClient
    private lateinit var database: MongoDatabase

    fun configure(connection: String, database: String) {
        client = MongoClients.create(
            MongoClientSettings.builder()
                // We want to use readable UUIDs as primary ids.
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(connection)).build()
        )
        this.database = client.getDatabase(database)
    }

    fun client(): MongoClient {
        return client
    }

    fun database(): MongoDatabase {
        return database
    }
}

fun Application.configureMongo() {
    val connection = environment.config.propertyOrNull("mongo.connection")?.getString() ?: ""
    val database = environment.config.propertyOrNull("mongo.database")?.getString() ?: ""
    MongoDbConfig.configure(connection, database)

    MongoMigration.execute(MongoDbConfig.client(), database)
}
