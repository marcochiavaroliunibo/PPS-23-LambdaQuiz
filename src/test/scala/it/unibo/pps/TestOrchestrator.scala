package it.unibo.pps

import it.unibo.pps.business.*
import it.unibo.pps.controller.{GameControllerTests, RoundControllerTests, UserControllerTests}
import org.scalatest.{BeforeAndAfterAll, Suite}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

class TestOrchestrator extends Suite with BeforeAndAfterAll:
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  private val businessSuites = IndexedSeq(
    new UserRepositoryTests,
    new GameRepositoryTests,
    new QuestionRepositoryTests,
    new RoundRepositoryTests
  )

  private val controllerSuites = IndexedSeq(
    new UserControllerTests,
    new GameControllerTests,
    new RoundControllerTests
  )

  override def beforeAll(): Unit =
    logger.info("ACTIVATING TEST MODE FOR THE DATABASE...")
    ConnectionMongoDB.activateDBTestMode()
    logger.info("CONNECTING TO THE TEST DATABASE...")
    wait(ConnectionMongoDB.initiateDatabaseConnection())
    logger.info("CLEANING UP THE DATABASE...")
    wait(TestDataInitializer.cleanData)
    logger.info("INITIALIZING DATA FOR TESTS...")
    wait(TestDataInitializer.initData)

  override def nestedSuites: IndexedSeq[Suite] =
    logger.info("EXECUTING ALL TESTS...")
    businessSuites ++ controllerSuites

  override def afterAll(): Unit =
    logger.info("CLOSING THE CONNECTION TO THE DATABASE...")
    wait(ConnectionMongoDB.closeConnection())

  private def wait[T](f: => Future[T]): Unit = Await.result(f, 5.seconds)

end TestOrchestrator
