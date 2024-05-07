package it.unibo.pps

import it.unibo.pps.business.*
import it.unibo.pps.controller.{CategoryControllerTests, GameControllerTests, RoundControllerTests, UserControllerTests}
import org.scalatest.{BeforeAndAfterAll, Suite}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

/** Questa classe funge da orchestratore per l'esecuzione degli Unit tests, eseguendo prima le operazioni di pulizia e
  * inizializzazione del database.
  */
class TestOrchestrator extends Suite with BeforeAndAfterAll:
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  // Esegue le operazioni preliminari di pulizia ed inizializzazione del database
  override def beforeAll(): Unit =
    logger.info("CLEANING UP THE DATABASE...")
    wait(TestDataInitializer.cleanData)
    logger.info("INITIALIZING DATA FOR TESTS...")
    wait(TestDataInitializer.initData)

  // Consente di specificare quelli classi di test devono essere eseguite per mezzo di una sequenza di Suite
  override def nestedSuites: IndexedSeq[Suite] =
    logger.info("EXECUTING ALL TESTS...")
    val businessSuites = IndexedSeq(
      new UserRepositoryTests,
      new GameRepositoryTests,
      new QuestionRepositoryTests,
      new RoundRepositoryTests
    )
    val controllerSuites = IndexedSeq(
      new UserControllerTests,
      new CategoryControllerTests,
      new GameControllerTests,
      new RoundControllerTests
    )
    businessSuites ++ controllerSuites

  // Una volta che tutti i test sono stati eseguiti, chiude la connessione al database
  override def afterAll(): Unit =
    logger.info("CLOSING THE CONNECTION TO THE DATABASE...")
    wait(ConnectionMongoDB.closeConnection())

  /** Metodo di utilità per attendere il completamento di una [[Future]], bloccando il thread corrente.
    *
    * Consente di evitare la ripetizione del codice per l'attesa di una [[Future]].
    *
    * @param f
    *   funzione che restituisce la [[Future]] da attendere
    * @tparam T
    *   tipo del valore restituito dalla Future. Sebbene non utilizzato, è necessario per la compilazione
    */
  private def wait[T](f: => Future[T]): Unit = Await.result(f, 5.seconds)

end TestOrchestrator
