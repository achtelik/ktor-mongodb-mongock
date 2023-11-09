package it.achtelik.ktor_mongodb_mongock.basics.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClients
import io.mongock.driver.mongodb.reactive.driver.MongoReactiveDriver
import io.mongock.runner.standalone.MongockStandalone
import org.bson.UuidRepresentation

class MongoMigration {
    companion object {
        fun execute(connection: String, database: String) {
            // Kotlin Coroutine is based on the reactive client.
            val client = MongoClients.create(
                MongoClientSettings.builder()
                    // We want to use readable UUIDs as primary ids.
                    .uuidRepresentation(UuidRepresentation.STANDARD)
                    .applyConnectionString(ConnectionString(connection)).build()
            )

            MongockStandalone.builder().setDriver(MongoReactiveDriver.withDefaultLock(client, database))
                .addMigrationScanPackage("${this::class.java.packageName}.migration")
                // If you activate TransactionEnabled, you have to use MongoDB as replica set.
                .setTransactionEnabled(false)
                .buildRunner()
                .execute()
        }
    }
}
