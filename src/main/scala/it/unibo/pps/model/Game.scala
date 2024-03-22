package it.unibo.pps.model

import it.unibo.pps.business.UserRepository
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.time.LocalDateTime
import java.util.UUID
import scala.util.Try

case class Game(
    players: List[User],
    completed: Boolean,
    lastUpdate: LocalDateTime,
    categories: List[Category],
    id: Option[UUID] = None
) {
  private val _id: UUID = id.getOrElse(UUID.randomUUID())

  def getID: String = _id.toString
  def getCompleted: Boolean = completed
  def getLastUpdate: LocalDateTime = lastUpdate

  def getCategories: List[Category] = categories

}

object Game {
  implicit object GameReader extends BSONDocumentReader[Game]:
    def readDocument(doc: BSONDocument): Try[Game] =
      for
        id <- doc.getAsTry[String]("_id")
        players <- doc.getAsTry[List[User]]("players")
        completed <- doc.getAsTry[Boolean]("completed")
        lastUpdate <- doc.getAsTry[LocalDateTime]("lastUpdate")
        categories <- doc.getAsTry[List[String]]("categories")
      yield Game(players, completed, lastUpdate, categories.map(Category.valueOf), Some(UUID.fromString(id)))

  implicit object GameWriter extends BSONDocumentWriter[Game]:
    override def writeTry(game: Game): Try[BSONDocument] =
      for
        id <- Try(game.getID)
        players <- Try(game.players)
        completed <- Try(game.getCompleted)
        lastUpdate <- Try(game.getLastUpdate)
        categories <- Try(game.getCategories.map(_.toString))
      yield BSONDocument(
        "_id" -> id,
        "players" -> players,
        "completed" -> completed,
        "lastUpdate" -> lastUpdate,
        "categories" -> categories
      )
}
