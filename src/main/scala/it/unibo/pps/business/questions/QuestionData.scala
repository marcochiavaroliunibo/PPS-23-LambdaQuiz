package it.unibo.pps.business.questions

import it.unibo.pps.business.{ConnectionMongoDB, Repository}
import it.unibo.pps.model.{Category, Question}

import scala.concurrent.ExecutionContext.Implicits.global

trait QuestionData extends Repository[Question] {
  def getQuestionsByCategory(category: Category): Option[?]

}
