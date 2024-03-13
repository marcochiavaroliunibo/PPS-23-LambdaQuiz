package it.unibo.pps.business

import org.slf4j.{Logger, LoggerFactory}
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}

import scala.concurrent.Future

/**
 * Questo object si occupa di fornire i servizi di connessione
 * al database MongoDB
 */
object ConnectionMongoDB {
    private val logger: Logger = LoggerFactory.getLogger(getClass)
    // Variabili per la connessione
    private val connectionString = "mongodb+srv://user-login:marco1234@cluster0.9jwsjr8.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    private val databaseName = "LambdaQuiz"
    private val mongoDriver = new AsyncDriver

    import scala.concurrent.ExecutionContext.Implicits.global
    logger.info(s"Trying to connect to $databaseName database...")
    private val database = for
        mongoUri <- MongoConnection.fromString(connectionString)
        connection <- mongoDriver.connect(mongoUri)
        db <- connection.database(databaseName)
    yield db

    database.onComplete {
        case resolution => logger.info(s"Successfully connected to $databaseName database")
        case null => logger.info(s"Error while connecting to $databaseName database")
    }

    def getDatabase: Future[DB] = database

    def closeConnection(): Unit = mongoDriver.close()
}
