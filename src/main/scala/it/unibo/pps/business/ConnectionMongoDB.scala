package it.unibo.pps.business

import org.slf4j.{Logger, LoggerFactory}
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}

import scala.compiletime.uninitialized
import scala.concurrent.Future

/** Questo object si occupa di fornire i servizi di connessione al database MongoDB
  */
object ConnectionMongoDB {
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  // Variabili per la connessione
  private val connectionString =
    "mongodb+srv://user-login:marco1234@cluster0.9jwsjr8.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
  private val databaseName = "LambdaQuiz"
  private val mongoDriver = AsyncDriver()

  import scala.concurrent.ExecutionContext.Implicits.global

  private var database: Future[DB] = uninitialized

  def initiateDatabaseConnection(): Unit =
    logger.info(s"Trying to connect to $databaseName database...")
    database = for
      mongoUri <- MongoConnection.fromString(connectionString)
      connection <- mongoDriver.connect(mongoUri)
      db <- connection.database(databaseName)
    yield db

    import scala.util.{Success, Failure}
    database.onComplete {
      case Success(db) =>
        logger.info(s"Successfully connected to ${db.name} database")
      case Failure(ex) =>
        logger.error(s"Error while connecting to $databaseName database", ex)
    }

  def getDatabase: Future[DB] = database

  def closeConnection(): Unit = mongoDriver.close()

}
