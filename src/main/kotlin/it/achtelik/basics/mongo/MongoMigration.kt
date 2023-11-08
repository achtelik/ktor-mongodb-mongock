package it.achtelik.basics.mongo

import com.mongodb.reactivestreams.client.MongoClient
import io.mongock.driver.mongodb.reactive.driver.MongoReactiveDriver
import io.mongock.runner.standalone.MongockStandalone

class MongoMigration {
    companion object {
        fun execute(client: MongoClient, database: String) {
            MongockStandalone.builder().setDriver(MongoReactiveDriver.withDefaultLock(client, database))
                .addMigrationScanPackage("it.achtelik.basics.mongo.migration")
                // If you activate TransactionEnabled, you have to use MongoDB as replica set.
                .setTransactionEnabled(false)
                .buildRunner()
                .execute()
        }
    }
}
