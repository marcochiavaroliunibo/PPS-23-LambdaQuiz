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
  

end CategoryRepository
