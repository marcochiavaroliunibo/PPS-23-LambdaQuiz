package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import scala.util.Try

/** Rappresenta il punteggio ottenuto da un utente.
  * @param user
  *   l'utente a cui è associato il punteggio
  * @param score
  *   il punteggio ottenuto
  */
@SuppressWarnings(Array("org.wartremover.warts.Var"))
case class Score(user: User, var score: Int)

/** Companion object per la classe [[Score]].
  *
  * Abilita la conversione da e verso BSONDocument in maniera trasparente, sfruttando il meccanismo degli impliciti.
  */
object Score:
  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
  def apply(user: User, score: Int = -1): Score = Score(user, score)

  implicit object ScoreReader extends BSONDocumentReader[Score]:
    /** Converte un documento BSON in un oggetto di tipo [[Score]].
      * @param doc
      *   il documento BSON da convertire
      * @return
      *   l'oggetto di tipo [[Score]] corrispondente al documento BSON
      */
    def readDocument(doc: BSONDocument): Try[Score] =
      for
        user <- doc.getAsTry[User]("user")
        score <- doc.getAsTry[Int]("score")
      yield Score(user, score)

  implicit object ScoreWriter extends BSONDocumentWriter[Score]:
    /** Converte un oggetto di tipo [[Score]] in un documento BSON.
      * @param score
      *   l'oggetto di tipo [[Score]] da convertire
      * @return
      *   il documento BSON corrispondente all'oggetto di tipo [[Score]]
      */
    override def writeTry(score: Score): Try[BSONDocument] =
      for
        user <- Try(score.user)
        score <- Try(score.score)
      yield BSONDocument(
        "user" -> user,
        "score" -> score
      )
end Score
