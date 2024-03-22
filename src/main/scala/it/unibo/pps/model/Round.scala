package it.unibo.pps.model

import it.unibo.pps.business.GameRepository
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.util.UUID
import scala.util.Try

case class Round(
    private val _relatedGameID: String,
    var pointUser1: Int,
    var pointUser2: Int,
    numberRound: Int,
    id: Option[UUID] = None
) {

  private val _id: UUID = id.getOrElse(UUID.randomUUID())

  def getID: String = _id.toString
  def relatedGameID: String = _relatedGameID
  def getPoint1: Int = pointUser1
  def getPoint2: Int = pointUser2
  def getNumberRound: Int = numberRound

  /** set punteggio di un player -1: non ha mai giocato il round 0: ha giocato il round ma non ha mai indovinato la
    * risposta 1+ ha giocato il round e indovinato 1+ risposte:
    */
  def setPoint(numberPlayer: Int, correct: Boolean): Unit =
    if numberPlayer == 1 then
      if getPoint1 == -1 then pointUser1 = 0
      if correct then pointUser1 = pointUser1 + 1
    else
      if getPoint2 == -1 then pointUser2 = 0
      if correct then pointUser2 = pointUser2 + 1
}

object Round {
  implicit object RoundReader extends BSONDocumentReader[Round]:
    def readDocument(doc: BSONDocument): Try[Round] =
      for
        id <- doc.getAsTry[String]("_id")
        gameID <- doc.getAsTry[String]("gameID")
        pointUser1 <- doc.getAsTry[Int]("pointUser1")
        pointUser2 <- doc.getAsTry[Int]("pointUser2")
        numberRound <- doc.getAsTry[Int]("numberRound")
      yield Round(gameID, pointUser1, pointUser2, numberRound, Some(UUID.fromString(id)))

  implicit object RoundWriter extends BSONDocumentWriter[Round]:
    override def writeTry(round: Round): Try[BSONDocument] =
      for
        id <- Try(round.getID)
        gameID <- Try(round.relatedGameID)
        pointUser1 <- Try(round.getPoint1)
        pointUser2 <- Try(round.getPoint2)
        numberRound <- Try(round.getNumberRound)
      yield BSONDocument(
        "_id" -> id,
        "gameID" -> gameID,
        "pointUser1" -> pointUser1,
        "pointUser2" -> pointUser2,
        "numberRound" -> numberRound
      )
}
