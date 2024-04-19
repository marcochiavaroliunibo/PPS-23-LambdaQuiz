package it.unibo.pps.business

import it.unibo.pps.TestDataInitializer.questionRepository
import it.unibo.pps.model.Category.Storia
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import org.scalatest.DoNotDiscover

@DoNotDiscover
class QuestionRepositoryTests extends AsyncFlatSpec with should.Matchers:

  "A Question" should "be read from the database by its category" in {
    questionRepository
      .getQuestionsByCategory(Storia)
      .map(_.exists(_.forall(_.category == Storia)) should be(true))
  }

end QuestionRepositoryTests
