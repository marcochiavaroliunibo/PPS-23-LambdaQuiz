package it.unibo.pps.business

import org.slf4j.{Logger, LoggerFactory}
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}

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

  private var database: Option[Future[DB]] = None

  def initiateDatabaseConnection(): Future[DB] =
    logger.info(s"Trying to connect to $databaseName database...")
    val futureDB = for
      mongoUri <- MongoConnection.fromString(connectionString)
      connection <- mongoDriver.connect(mongoUri)
      db <- connection.database(databaseName)
    yield db

    import scala.util.{Failure, Success}
    futureDB.onComplete {
      case Success(db) =>
        logger.info(s"Successfully connected to ${db.name} database")
      case Failure(ex) =>
        logger.error(s"Error while connecting to $databaseName database", ex)
    }
    database = Some(futureDB)
    futureDB

  def getDatabase: Future[DB] = database.getOrElse(initiateDatabaseConnection())

  def closeConnection(): Unit = mongoDriver.close()

}
