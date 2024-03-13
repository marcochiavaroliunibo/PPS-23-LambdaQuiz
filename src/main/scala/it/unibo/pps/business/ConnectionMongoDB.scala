package it.unibo.pps.business

import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros, document}

import scala.concurrent.Future

/**
 * Questo object si occupa di fornire i servizi di connessione
 * al database MongoDB
 */
object ConnectionMongoDB {
    println("Connecting to the database...")
    import scala.concurrent.ExecutionContext.Implicits.global
    // Variabili per la connessione
    private val connectionString = "mongodb+srv://user-login:marco1234@cluster0.9jwsjr8.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    private val databaseName = "LambdaQuiz"
    private val mongoDriver = new AsyncDriver

    private val database = for
        mongoUri <- MongoConnection.fromString(connectionString)
        connection <- mongoDriver.connect(mongoUri)
        db <- connection.database(databaseName)
    yield db

    database.onComplete {
        case resolution => println(s"Successfully connected to $databaseName database")
        case _ => println(s"Error while connecting to $databaseName database")
    }

    def getDatabase: Future[DB] = database

    def closeConnection(): Unit = mongoDriver.close()
}
