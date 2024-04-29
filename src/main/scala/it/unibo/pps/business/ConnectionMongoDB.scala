package it.unibo.pps.business

import it.unibo.pps.ECHandler
import org.slf4j.{Logger, LoggerFactory}
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}

import java.util.concurrent.Executors.newSingleThreadExecutor
import scala.concurrent.{ExecutionContext, Future}

/** Oggetto che si occupa di gestire la connessione al database MongoDB.
  */
@SuppressWarnings(Array("org.wartremover.warts.Var"))
object ConnectionMongoDB {
  given ExecutionContext = ECHandler.createExecutor
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  /** Stringa di connessione al database. */
  private val connectionString =
    "mongodb+srv://user-login:marco1234@cluster0.9jwsjr8.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"

  /** Nome del database. */
  
  private var databaseName = "LambdaQuiz"

  /** Driver asincrono per la connessione al database. */
  private val mongoDriver = AsyncDriver()

  /** Future che rappresenta la connessione al database. */
  private var database: Option[Future[DB]] = None

  /** Metodo che inizializza la connessione al database.
    *
    * @return
    *   Future di [[DB]] che rappresenta la connessione al database
    */
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

  /** Metodo che restituisce la connessione al database, se presente, altrimenti la inizializza.
    * @param testMode
    *   se true imposto il database di test
    * @return
    *   Future di [[DB]] che rappresenta la connessione al database
    */
  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
  def getDatabase(testMode: Boolean = false): Future[DB] = {
    if testMode then activateDBTestMode()
    database.getOrElse(initiateDatabaseConnection())
  }

  /** Metodo che chiude la connessione al database. */
  def closeConnection(): Future[Unit] = mongoDriver.close()

  /** Metodo per attivare la modalità di test. */
  def activateDBTestMode(): Unit = databaseName = "LambdaQuiz-test"

}
