package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.util.UUID
import scala.util.Try

case class Category(name: String, id: Option[UUID] = None) {
  private val _id: UUID = id.getOrElse(UUID.randomUUID())

  def getName: String = name
  def getID: String = _id.toString

}

object Category {
  implicit object CategoryReader extends BSONDocumentReader[Category]:
    def readDocument(doc: BSONDocument): Try[Category] = for
      id <- doc.getAsTry[String]("_id")
      name <- doc.getAsTry[String]("name")
    yield Category(name, Some(UUID.fromString(id)))

  implicit object Categoryriter extends BSONDocumentWriter[Category]:
    override def writeTry(category: Category): Try[BSONDocument] = for
      id <- Try(category.getID)
      name <- Try(category.getName)
    yield BSONDocument("_id" -> id, "name" -> name)
}
