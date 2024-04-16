package it.unibo.pps.business

import it.unibo.pps.TestDataInitializer.{question, questionRepository}
import it.unibo.pps.model.Question
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import reactivemongo.api.bson.BSONDocument

class QuestionRepositoryTests extends AsyncFlatSpec with should.Matchers:

  "A Question" should "be read from the database by its text and category" in {
    questionRepository
      .readOne(
        BSONDocument(
          "text" -> question.text,
          "category" -> question.category.toString
        )
      )
      .map(_.exists(_.id == question.id) should be(true))
  }

end QuestionRepositoryTests
