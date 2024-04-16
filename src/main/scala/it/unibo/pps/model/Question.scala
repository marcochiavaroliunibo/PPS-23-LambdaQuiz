package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.util.UUID
import scala.util.Try

/** Rappresenta una domanda all'interno del gioco.
  * @param text
  *   testo della domanda
  * @param answers
  *   lista di risposte possibili
  * @param correctAnswer
  *   indice della risposta corretta
  * @param category
  *   categoria della domanda
  * @param id
  *   identificativo univoco della domanda
  */
case class Question(
    text: String,
    answers: List[String],
    correctAnswer: Int,
    category: Category,
    id: String
)

/** Companion object per la classe [[Question]].
  *
  * Abilita la conversione da e verso BSONDocument in maniera trasparente, sfruttando il meccanismo degli impliciti.
  */
object Question {
  def apply(
    text: String,
    answers: List[String],
    correctAnswer: Int,
    category: Category,
    id: Option[UUID] = None
  ): Question =
    Question(text, answers, correctAnswer, category, id.getOrElse(UUID.randomUUID()).toString)

  implicit object QuestionReader extends BSONDocumentReader[Question]:
    /** Converte un documento BSON in un oggetto di tipo [[Question]].
      *
      * @param doc
      *   il documento BSON da convertire
      * @return
      *   l'oggetto di tipo [[Question]] corrispondente al documento BSON
      */
    def readDocument(doc: BSONDocument): Try[Question] =
      for
        id <- doc.getAsTry[String]("_id")
        text <- doc.getAsTry[String]("text")
        answers <- doc.getAsTry[List[String]]("answers")
        category <- doc.getAsTry[String]("category")
        correctAnswer <- doc.getAsTry[Int]("correctAnswer")
      yield Question(text, answers, correctAnswer, Category.valueOf(category), id)

  implicit object QuestionWriter extends BSONDocumentWriter[Question]:
    /** Converte un oggetto di tipo [[Question]] in un documento BSON.
      *
      * @param question
      *   l'oggetto di tipo [[Question]] da convertire
      * @return
      *   il documento BSON corrispondente all'oggetto di tipo [[Score]]
      */
    override def writeTry(question: Question): Try[BSONDocument] = for
      id <- Try(question.id)
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
