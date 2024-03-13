package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.util.UUID
import scala.util.Try

case class User(nickname: String, password: String, id: Option[UUID] = None) {
    private val _id: UUID = id.getOrElse(UUID.randomUUID())

    def getID: String = _id.toString
    def getName: String = nickname
    def getPassword: String = password
}

object User {
    implicit object UserReader extends BSONDocumentReader[User]:
        def readDocument(doc: BSONDocument): Try[User] = for
            id <- doc.getAsTry[String]("_id")
            username <- doc.getAsTry[String]("username")
            password <- doc.getAsTry[String]("password")
        yield User(username, password, Some(UUID.fromString(id)))

    implicit object UserWriter extends BSONDocumentWriter[User]:
        override def writeTry(user: User): Try[BSONDocument] = for
            id <- Try(user.getID)
            username <- Try(user.getName)
            password <- Try(user.getPassword)
        yield BSONDocument("_id" -> id, "username" -> username, "password"-> password)
}
