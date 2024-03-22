package it.unibo.pps.model

import it.unibo.pps.business.UserRepository
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.time.LocalDateTime
import java.util.UUID
import scala.util.Try

case class Game(
    user1: User,
    user2: User,
    var completed: Boolean,
    lastUpdate: LocalDateTime,
    categories: List[Category],
    id: Option[UUID] = None
) {
  private val _id: UUID = id.getOrElse(UUID.randomUUID())

  def getID: String = _id.toString
  def getUser1: User = user1
  def getUser2: User = user2
  def getCompleted: Boolean = completed
  def getLastUpdate: LocalDateTime = lastUpdate
  def getCategories: List[Category] = categories

  def setCompleted(status: Boolean): Unit = completed = status

}

object Game {
  implicit object GameReader extends BSONDocumentReader[Game]:
    def readDocument(doc: BSONDocument): Try[Game] =
      for
        id <- doc.getAsTry[String]("_id")
        user1 <- doc.getAsTry[User]("user1")
        user2 <- doc.getAsTry[User]("user2")
        completed <- doc.getAsTry[Boolean]("completed")
        lastUpdate <- doc.getAsTry[LocalDateTime]("lastUpdate")
        categories <- doc.getAsTry[List[String]]("categories")
      yield Game(user1, user2, completed, lastUpdate, categories.map(Category.valueOf), Some(UUID.fromString(id)))

  implicit object GameWriter extends BSONDocumentWriter[Game]:
    override def writeTry(game: Game): Try[BSONDocument] =
      for
        id <- Try(game.getID)
        user1 <- Try(game.getUser1)
        user2 <- Try(game.getUser2)
        completed <- Try(game.getCompleted)
        lastUpdate <- Try(game.getLastUpdate)
        categories <- Try(game.getCategories.map(_.toString))
      yield BSONDocument(
        "_id" -> id,
        "user1" -> user1,
        "user2" -> user2,
        "completed" -> completed,
        "lastUpdate" -> lastUpdate,
        "categories" -> categories
      )
}
