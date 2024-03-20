package it.unibo.pps.business

import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait Repository[T]:
  protected def collection: Future[BSONCollection]
  
  def readById(id: String)(implicit reader: BSONDocumentReader[T]): Future[Option[T]] =
    readOne(BSONDocument("_id" -> id))
    
  def create(t: T)(implicit writer: BSONDocumentWriter[T]): Future[Unit] =
    collection
      .map(_.insert.one(t))
  
  def readOne(query: BSONDocument)(implicit reader: BSONDocumentReader[T]): Future[Option[T]] =
    collection
      .flatMap(_.find(query).one[T])
      .map {
        case Some(user) => Some(user)
        case None       => None
      }
      .recover { case f: Throwable =>
        f.printStackTrace()
        None
      }

  def readMany(query: BSONDocument)(implicit reader: BSONDocumentReader[T]): Future[Option[List[T]]] =
    collection
      .flatMap(_.find(query).cursor[T]().collect[List]())
      .map {
        case l: List[T] if l.nonEmpty => Some(l)
        case _                        => None
      }
      .recover { case f: Throwable =>
        f.printStackTrace()
        None
      }

end Repository
