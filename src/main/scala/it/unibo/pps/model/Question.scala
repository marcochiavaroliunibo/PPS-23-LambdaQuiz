package it.unibo.pps.model

import it.unibo.pps.business.{CategoryRepository, Repository}
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

case class Question(
    text: String,
    answers: List[String],
    correctAnswer: Int,
    category: Category,
    id: Option[UUID] = None
) {
  private val _id: UUID = id.getOrElse(UUID.randomUUID())

  def getID: String = _id.toString
  
  def getText: String = text
  
  def getAnswers: List[String] = answers

  def getCorrectAnswerNumber: Int = correctAnswer

  def getCorrectAnswerString: String = answers(correctAnswer - 1)

  def getCategory: Category = category

}

object Question {
  implicit object QuestionReader extends BSONDocumentReader[Question]:

    protected val categoryRepository = new CategoryRepository

    def readDocument(doc: BSONDocument): Try[Question] =
      var category: Category = null
      val categoryFuture = Await.result(categoryRepository.read(doc.getAsTry[String]("category").get), Duration.Inf)
      if categoryFuture.isDefined then category = categoryFuture.get
      
      for
        id <- doc.getAsTry[String]("_id")
        text <- doc.getAsTry[String]("text")
        answer1 <- doc.getAsTry[String]("answer1")
        answer2 <- doc.getAsTry[String]("answer2")
        answer3 <- doc.getAsTry[String]("answer3")
        answer4 <- doc.getAsTry[String]("answer4")
        correctAnswer <- doc.getAsTry[Int]("correctAnswer")
      yield Question(text, List(answer1, answer2, answer3, answer4), correctAnswer, category, Some(UUID.fromString(id)))

  implicit object QuestionWriter extends BSONDocumentWriter[Question]:
    override def writeTry(question: Question): Try[BSONDocument] = for
      id <- Try(question.getID)
      text <- Try(question.getText)
      answer1 <- Try(question.getAnswers.head)
      answer2 <- Try(question.getAnswers(1))
      answer3 <- Try(question.getAnswers(2))
      answer4 <- Try(question.getAnswers(3))
      correctAnswer <- Try(question.getCorrectAnswerNumber)
      category <- Try(question.getCategory.getID)
    yield BSONDocument("_id" -> id, "text" -> text, "answer1" -> answer1, "answer2" -> answer2, "answer3" -> answer3, "answer4" -> answer4,
      "correctAnswer" -> correctAnswer, "category" -> category)
}