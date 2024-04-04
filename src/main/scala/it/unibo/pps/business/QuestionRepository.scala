package it.unibo.pps.business

import it.unibo.pps.model.{Category, Question}
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class QuestionRepository extends Repository[Question]:
  protected val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase().map(_.collection("questions"))
  def getQuestionsByCategory(category: Category): Future[Option[List[Question]]] =
    readMany(
      BSONDocument(
        "category" -> category.toString
      )
    )

end QuestionRepository
