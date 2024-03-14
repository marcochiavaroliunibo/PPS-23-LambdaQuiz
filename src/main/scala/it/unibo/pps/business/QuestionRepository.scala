package it.unibo.pps.business

import it.unibo.pps.business.{ConnectionMongoDB, Repository}
import it.unibo.pps.model.{Category, Question}
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class QuestionRepository extends Repository[Question]:
  protected val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase.map(_.collection("questions"))

  override def create(question: Question): Future[Unit] =
    this.collection
      .map(_.insert.one(question))

  override def read(id: String): Future[Option[Question]] =
    this.collection
      .map(
        _.find(
          BSONDocument(
            "_id" -> id
          )
        ).one[Question]
      )
      .flatMap(_.andThen {
        case Failure(e) => e.printStackTrace()
        case Success(Some(u)) => Some(u)
        case Success(None) => None
      })
    
  def getQuestionsByCategory(category: Category): Future[List[Question]] =
    this.collection
      .map(
        _.find(
          BSONDocument(
            "category" -> category.getID
          )
        ).cursor[Question]().collect[List]()
      )
      .flatMap(_.andThen {
        case Failure(e) => e.printStackTrace()
        case Success(List) => List
      })

end QuestionRepository
