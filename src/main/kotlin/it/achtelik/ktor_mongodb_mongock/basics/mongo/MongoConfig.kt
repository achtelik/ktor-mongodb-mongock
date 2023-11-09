package it.achtelik.ktor_mongodb_mongock.basics.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.application.*
import org.bson.UuidRepresentation

object MongoDbConfig {
    private lateinit var client: MongoClient
    private lateinit var database: MongoDatabase

    fun configure(connection: String, database: String) {
        client = MongoClient.create(
            MongoClientSettings.builder()
                // We want to use UUIDs as type at our DOC objects.
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(connection)).build()
        )
        MongoDbConfig.database = client.getDatabase(database)
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

    MongoMigration.execute(connection, database)
}
