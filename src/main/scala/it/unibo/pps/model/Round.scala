package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.util.UUID
import scala.util.Try

/** Rappresenta il round di una partita.
  * @param relatedGameID
  *   identificativo della partita a cui il round è associato
  * @param scores
  *   lista dei punteggi dei giocatori
  * @param numberRound
  *   numero del round
  * @param id
  *   identificativo del round. Se non specificato, viene generato automaticamente
  */
case class Round(
    relatedGameID: String,
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

/** Companion object per la classe [[Round]].
  *
  * Abilita la conversione da e verso BSONDocument in maniera trasparente, sfruttando il meccanismo degli impliciti.
  */
object Round {
  implicit object RoundReader extends BSONDocumentReader[Round]:
    /** Converte un documento BSON in un oggetto di tipo [[Round]].
      * @param doc
      *   il documento BSON da convertire
      * @return
      *   l'oggetto di tipo [[Round]] corrispondente al documento BSON
      */
    def readDocument(doc: BSONDocument): Try[Round] =
      for
        id <- doc.getAsTry[String]("_id")
        gameID <- doc.getAsTry[String]("gameID")
        scores <- doc.getAsTry[List[Score]]("scores")
        numberRound <- doc.getAsTry[Int]("numberRound")
      yield Round(gameID, scores, numberRound, Some(UUID.fromString(id)))

  implicit object RoundWriter extends BSONDocumentWriter[Round]:
    /** Converte un oggetto di tipo [[Round]] in un documento BSON.
      *
      * @param round
      *   l'oggetto di tipo [[Round]] da convertire
      * @return
      *   il documento BSON corrispondente all'oggetto di tipo [[Score]]
      */
    override def writeTry(round: Round): Try[BSONDocument] =
      for
        id <- Try(round.getID)
        gameID <- Try(round.relatedGameID)
        scores <- Try(round.scores)
        numberRound <- Try(round.numberRound)
      yield BSONDocument(
        "_id" -> id,
        "gameID" -> gameID,
        "scores" -> scores,
        "numberRound" -> numberRound
      )
}
