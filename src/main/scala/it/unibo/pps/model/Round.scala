package it.unibo.pps.model

import it.unibo.pps.business.GameRepository
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}
import it.unibo.pps.model.Score
import java.util.UUID
import scala.util.Try

case class Round(
    _relatedGameID: String,
    scores: List[Score],
    numberRound: Int,
    id: Option[UUID] = None
) {

  private val _id: UUID = id.getOrElse(UUID.randomUUID())

  def getID: String = _id.toString

  /** set punteggio di un player -1: non ha mai giocato il round 0: ha giocato il round ma non ha mai indovinato la
    * risposta 1+ ha giocato il round e indovinato 1+ risposte:
    */
  def setPoint(player: User, correct: Boolean): Unit = {
    val point = scores.filter(_.user.username == player.username).head
    if point.score == -1 then point.score = 0
    if correct then point.score = point.score + 1
  }
  
}

object Round {
  implicit object RoundReader extends BSONDocumentReader[Round]:
    def readDocument(doc: BSONDocument): Try[Round] =
      for
        id <- doc.getAsTry[String]("_id")
        gameID <- doc.getAsTry[String]("gameID")
        scores <- doc.getAsTry[List[Score]]("scores")
        numberRound <- doc.getAsTry[Int]("numberRound")
      yield Round(gameID, scores, numberRound, Some(UUID.fromString(id)))

  implicit object RoundWriter extends BSONDocumentWriter[Round]:
    override def writeTry(round: Round): Try[BSONDocument] =
      for
        id <- Try(round.getID)
        gameID <- Try(round._relatedGameID)
        scores <- Try(round.scores)
        numberRound <- Try(round.numberRound)
      yield BSONDocument(
        "_id" -> id,
        "gameID" -> gameID,
        "scores" -> scores,
        "numberRound" -> numberRound
      )
}
