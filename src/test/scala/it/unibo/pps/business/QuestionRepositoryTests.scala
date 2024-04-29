package it.unibo.pps.business

import it.unibo.pps.TestDataInitializer.{questionRepository, questions}
import it.unibo.pps.model.Category.{Geografia, Storia}
import org.scalatest.DoNotDiscover
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

@DoNotDiscover
class QuestionRepositoryTests extends AsyncFlatSpec with should.Matchers:

  @SuppressWarnings(Array("org.wartremover.warts.IterableOps"))
  private val historyQuestion = questions.filter(_.category == Storia).head

  "QuestionRepository" should "be able to read all questions belonging to a category" in {
    questionRepository
      .getQuestionsByCategory(Geografia)
      .map(_.map(_.forall(_.category == Geografia) should be(true)).getOrElse(fail("No questions found")))
  }

  it should "be able to read a specific historyQuestion based on its category" in {
    questionRepository
      .getQuestionsByCategory(Storia)
      .map(_.map(_ should contain(historyQuestion)).getOrElse(fail("No questions found")))
  }

end QuestionRepositoryTests
