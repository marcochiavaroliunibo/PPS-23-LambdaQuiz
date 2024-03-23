package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}

import java.util.UUID
import scala.util.Try

case class User(username: String, password: String, id: Option[UUID] = None) {
  private val _id: UUID = id.getOrElse(UUID.randomUUID())

  def getID: String = _id.toString
}

object User {
  implicit object UserReader extends BSONDocumentReader[User]:
    def readDocument(doc: BSONDocument): Try[User] =
      for
        id <- doc.getAsTry[String]("_id")
        username <- doc.getAsTry[String]("username")
        password <- doc.getAsTry[String]("password")
      yield User(username, password, {
        println(id)
        println(Some(UUID.fromString(id)))
        Some(UUID.fromString(id))
      })

  implicit object UserWriter extends BSONDocumentWriter[User]:
    override def writeTry(user: User): Try[BSONDocument] =
      for
        id <- Try(user.getID)
        username <- Try(user.username)
        password <- Try(user.password)
      yield BSONDocument("_id" -> id, "username" -> username, "password" -> password)
}
