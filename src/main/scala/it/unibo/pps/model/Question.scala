package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.util.UUID
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

}

object Question {
  implicit object QuestionReader extends BSONDocumentReader[Question]:
    def readDocument(doc: BSONDocument): Try[Question] = 
      for
        id <- doc.getAsTry[String]("_id")
        text <- doc.getAsTry[String]("text")
        answers <- doc.getAsTry[List[String]]("answers")
        category <- doc.getAsTry[String]("category")
        correctAnswer <- doc.getAsTry[Int]("correctAnswer")
      yield Question(text, answers, correctAnswer, Category.valueOf(category), Some(UUID.fromString(id)))

  implicit object QuestionWriter extends BSONDocumentWriter[Question]:
    override def writeTry(question: Question): Try[BSONDocument] = for
      id <- Try(question.getID)
      text <- Try(question.text)
      answers <- Try(question.answers)
      correctAnswer <- Try(question.correctAnswer)
      category <- Try(question.category.toString)
    yield BSONDocument(
      "_id" -> id,
      "text" -> text,
      "answers" -> answers,
      "correctAnswer" -> correctAnswer,
      "category" -> category
    )
}
