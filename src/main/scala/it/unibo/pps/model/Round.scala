package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.util.UUID
import scala.util.Try

/** Rappresenta il round di una partita.
  * @param gameId
  *   identificativo della partita a cui il round è associato
  * @param scores
  *   lista dei punteggi dei giocatori
  * @param numberRound
  *   numero del round
  * @param id
  *   identificativo del round
  */
case class Round(gameId: String, scores: List[Score], numberRound: Int, id: String) {

  /** set punteggio di un player -1: non ha mai giocato il round 0: ha giocato il round ma non ha mai indovinato la
    * risposta 1+ ha giocato il round e indovinato 1+ risposte:
    */

  /** Aggiorna il punteggio di un giocatore.
    *
    * In particolare, se il giocatore non ha ancora giocato il round, il suo punteggio è -1. Se ha giocato il round
    * rispondendo in maniera errata, il suo punteggio è 0. Se, invece, ha giocato rispondendo correttamente, il
    * punteggio sarà pari al numero di domande corrette.
    *
    * @param player
    *   il giocatore a cui aggiornare il punteggio
    * @param correct
    *   booleano che indica se il giocatore ha risposto correttamente
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
  def apply(gameId: String, scores: List[Score], numberRound: Int, id: Option[String] = None): Round =
    Round(gameId, scores, numberRound, id.getOrElse(UUID.randomUUID().toString))

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
        gameID <- doc.getAsTry[String]("gameId")
        scores <- doc.getAsTry[List[Score]]("scores")
        numberRound <- doc.getAsTry[Int]("numberRound")
      yield Round(gameID, scores, numberRound, id)

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
        id <- Try(round.id)
        gameID <- Try(round.gameId)
        scores <- Try(round.scores)
        numberRound <- Try(round.numberRound)
      yield BSONDocument(
        "_id" -> id,
        "gameId" -> gameID,
        "scores" -> scores,
        "numberRound" -> numberRound
      )
}
