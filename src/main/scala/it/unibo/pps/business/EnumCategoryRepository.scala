package it.unibo.pps.business

import it.unibo.pps.model.EnumCategory
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class EnumCategoryRepository extends Repository[EnumCategory]:
  override val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase.map(_.collection("enums"))
  
  def insertCategory(c: EnumCategory): Future[Unit] =
    create(c)
    
  def getCategoryFromName(name: String): Future[Option[EnumCategory]] =
    readOne(BSONDocument(
      "name" -> name
    ))
end EnumCategoryRepository

