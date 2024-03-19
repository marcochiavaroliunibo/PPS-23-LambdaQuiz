package it.unibo.pps.business

import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.Future

trait Repository[T]:
  protected val collection: Future[BSONCollection]

  def create(t: T): Future[Unit]
  def read(id: String): Future[Option[T]]
  // def readOne(query: BSONDocument): Future[Option[T]]
  // def readMany(query: BSONDocument): Future[Option[List[T]]]
end Repository
