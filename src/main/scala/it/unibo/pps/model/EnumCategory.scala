package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import scala.util.Try

enum EnumCategory:
  case Scienze
  case Geografia
  case Storia
  case CulturaGenerale
end EnumCategory

object EnumCategory:
  implicit object ECategoryReader extends BSONDocumentReader[EnumCategory]:
    def readDocument(doc: BSONDocument): Try[EnumCategory] =
      for name <- doc.getAsTry[String]("name")
      yield EnumCategory.valueOf(name)

  implicit object ECategoryriter extends BSONDocumentWriter[EnumCategory]:
    override def writeTry(category: EnumCategory): Try[BSONDocument] =
      for name <- Try(category.toString)
      yield BSONDocument("name" -> name)
end EnumCategory
