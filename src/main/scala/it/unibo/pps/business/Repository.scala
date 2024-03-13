package it.unibo.pps.business

import scala.concurrent.Future
import reactivemongo.api.bson.collection.BSONCollection

trait Repository[T]:
  protected val collection: Future[BSONCollection]

  def create(t: T): Future[Unit]
  def read(id: String): Future[Option[T]]
end Repository

