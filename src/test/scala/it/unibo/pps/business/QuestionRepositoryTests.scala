package it.unibo.pps.business

import it.unibo.pps.model.{Category, Question}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import reactivemongo.api.bson.BSONDocument

class QuestionRepositoryTests extends AsyncFlatSpec with should.Matchers:

  private val questionRepository = new QuestionRepository
  private val question = new Question(
    "test domanda",
    List("risposta 1", "risposta 2", "risposta 3", "Risposta 4"),
    1,
    new Category("test categoria")
  )

  "A question" should "eventually be inserted in the database" in {
    questionRepository
      .create(question)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    val idQuery = BSONDocument("_id" -> question.getID)
    questionRepository.read(question.getID).map(_.exists(_.getText == question.getText) should be(true))
  }

end QuestionRepositoryTests
