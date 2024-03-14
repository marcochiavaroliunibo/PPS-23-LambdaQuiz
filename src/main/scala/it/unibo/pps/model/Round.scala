package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.util.UUID
import scala.util.Try

case class Round(game: Game, pointUser1: Int, pointUser2: Int, numberRound: Int,  id: Option[UUID] = None) {

  private val _id: UUID = id.getOrElse(UUID.randomUUID())

  def getID: String = _id.toString
  def getGame: Game = game
  def getPoint1: Int = pointUser1
  def getPoint2: Int = pointUser2
  def getNumberRound: Int = numberRound

}

object Round {
  implicit object RoundReader extends BSONDocumentReader[Round]:
    def readDocument(doc: BSONDocument): Try[Round] = for
      id <- doc.getAsTry[String]("_id")
      game <- doc.getAsTry[Game]("game")
      pointUser1 <- doc.getAsTry[Int]("pointUser1")
      pointUser2 <- doc.getAsTry[Int]("pointUser2")
      numberRound <- doc.getAsTry[Int]("numberRound")
    yield Round(game, pointUser1, pointUser2, numberRound, Some(UUID.fromString(id)))

  implicit object RoundWriter extends BSONDocumentWriter[Round]:
    override def writeTry(round: Round): Try[BSONDocument] = for
      id <- Try(round.getID)
      game <- Try(round.getGame.getID)
      pointUser1 <- Try(round.getPoint1)
      pointUser2 <- Try(round.getPoint2)
      numberRound <- Try(round.getNumberRound)
    yield BSONDocument("_id" -> id, "game" -> game, "pointUser1" -> pointUser1, "pointUser2" -> pointUser2, "numberRound" -> numberRound)
}