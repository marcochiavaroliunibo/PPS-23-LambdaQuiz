package it.unibo.pps.business

import it.unibo.pps.model.{Category, Question}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import reactivemongo.api.bson.BSONDocument

class QuestionRepositoryTests extends AsyncFlatSpec with should.Matchers:
  import Category.*
  private val questionRepository = new QuestionRepository
  private val question = new Question(
    "quanti anni ha Berlusconi?",
    List("risposta 1", "risposta 2", "risposta 3"),
    1,
    CulturaGenerale
  )

  "A question" should "eventually be inserted in the database" in {
    questionRepository
      .create(question)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database by id" in {
    questionRepository
      .readById(question.getID)
      .map(_.isDefined should be(true))
  }

  it should "be read from the database by text and category" in {
    questionRepository
      .readOne(
        BSONDocument(
          "text" -> question.text,
          "category" -> CulturaGenerale.toString
        )
      )
      .map(_.exists(q => q.text == question.text && q.category == question.category) should be(true))
  }

end QuestionRepositoryTests
