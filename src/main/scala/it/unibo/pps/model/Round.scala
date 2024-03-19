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

  def setPoint(numberPlayer: Int, newPoint: Int): Unit =
    if numberPlayer == 1 then pointUser1 = newPoint
    else pointUser2 = newPoint
  
}

object Round {
  implicit object RoundReader extends BSONDocumentReader[Round]:

    protected val gameRepository = new GameRepository

    def readDocument(doc: BSONDocument): Try[Round] =
//      var game: Game = null
//      val gameFuture = Await.result(gameRepository.read(doc.getAsTry[String]("game").get), Duration.Inf)
//      if gameFuture.isDefined then game = gameFuture.get

      for
        id <- doc.getAsTry[String]("_id")
        gameID <- doc.getAsTry[String]("gameID")
        pointUser1 <- doc.getAsTry[Int]("pointUser1")
        pointUser2 <- doc.getAsTry[Int]("pointUser2")
        numberRound <- doc.getAsTry[Int]("numberRound")
      yield Round(gameID, pointUser1, pointUser2, numberRound, Some(UUID.fromString(id)))

  implicit object RoundWriter extends BSONDocumentWriter[Round]:
    override def writeTry(round: Round): Try[BSONDocument] = for
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
