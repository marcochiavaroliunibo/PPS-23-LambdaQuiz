package it.unibo.pps.business

import it.unibo.pps.business.{ConnectionMongoDB, QuestionRepository}
import it.unibo.pps.model.{Category, Question}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

class QuestionRepositoryTests extends AsyncFlatSpec with should.Matchers:
  "The application" should "connect to the database" in {
    ConnectionMongoDB.getDatabase.map(db => db.name shouldEqual "LambdaQuiz")
  }

  val question = new Question("test domanda", List("risposta 1", "risposta 2", "risposta 3", "Risposta 4"), 1, new Category("test categoria"))
  "A question" should "eventually be inserted in the database" in {
    val questionRepository = new QuestionRepository
    questionRepository
      .create(question)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    val questionRepository = new QuestionRepository
    val futureQuestion = questionRepository.read(question.getID)
    futureQuestion
      .map(_.exists(_.getText == question.getText) should be(true))
  }
end QuestionRepositoryTests
