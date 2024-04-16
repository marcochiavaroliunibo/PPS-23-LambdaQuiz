package it.unibo.pps

import it.unibo.pps.business.*
import org.scalatest.{BeforeAndAfterAll, Suite}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class TestOrchestrator extends Suite with BeforeAndAfterAll:
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  override def beforeAll(): Unit =
    logger.info("ACTIVATING TEST MODE FOR THE DATABASE...")
    ConnectionMongoDB.activateDBTestMode()
    logger.info("CONNECTING TO THE TEST DATABASE...")
    Await.result(ConnectionMongoDB.initiateDatabaseConnection(), 5.seconds)
    logger.info("CLEANING UP THE DATABASE...")
    Await.result(TestDataInitializer.cleanData(), 5.seconds)
    logger.info("INITIALIZING DATA FOR TESTS...")
    Await.result(TestDataInitializer.initData, 5.seconds)

  override def nestedSuites: IndexedSeq[Suite] =
    logger.info("EXECUTING ALL TESTS...")
    IndexedSeq(new UserRepositoryTests, new GameRepositoryTests, new RoundRepositoryTests, new QuestionRepositoryTests)

  override def afterAll(): Unit =
    logger.info("CLOSING THE CONNECTION TO THE DATABASE...")
    Await.result(ConnectionMongoDB.closeConnection(), 5.seconds)

end TestOrchestrator
