package it.unibo.pps.controller

import it.unibo.pps.business.QuestionRepository
import it.unibo.pps.model.{Category, Question}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Random

object QuestionController:
  
  private var question: Question = null
  private val questionRepository = new QuestionRepository
  
  def getRandomQuestionByCategory(category: Category): Question =
    val questionResult = Await.result(questionRepository.getQuestionsByCategory(category), Duration.Inf)
    val randomQuestion = Random.nextInt(questionResult.length - 1)
    questionResult(randomQuestion)

  def setQuestion(newQuestion: Question): Unit = question = newQuestion
  def getQuestion: Question = question
  
end QuestionController
