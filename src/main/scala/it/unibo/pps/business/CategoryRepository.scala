package it.unibo.pps.business

import it.unibo.pps.business.{ConnectionMongoDB, Repository}
import it.unibo.pps.model.Category
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class CategoryRepository extends Repository[Category]:
  protected val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase.map(_.collection("categories"))

  override def read(id: String): Future[Option[Category]] =
    this.collection
      .map(
        _.find(
          BSONDocument(
            "_id" -> id
          )
        ).one[Category]
      )
      .flatMap(_.andThen {
        case Failure(e) => e.printStackTrace()
        case Success(Some(u)) => Some(u)
        case Success(None) => None
      })

end CategoryRepository
