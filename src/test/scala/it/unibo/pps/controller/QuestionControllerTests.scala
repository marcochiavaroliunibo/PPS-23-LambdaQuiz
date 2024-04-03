package it.unibo.pps.controller

import it.unibo.pps.business.QuestionRepository
import it.unibo.pps.model.Category.Geografia
import it.unibo.pps.model.Question
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*

import java.time.LocalDateTime
import scala.concurrent.ExecutionContext.Implicits.global

class QuestionControllerTests extends AnyFlatSpec with should.Matchers:
  
  private val question = new Question("Domanda di test?", List("Risposta 1", "Risposta 2", "Risposta 3"), 1, Geografia)
  private val questionRepository = new QuestionRepository

  "QuestionController" should "be able to create a new question" in {
    QuestionController.createQuestion(question)
    questionRepository.readById(question.getID)
      .map(_.exists(_.getID == question.getID) should be(true))
  }


end QuestionControllerTests
