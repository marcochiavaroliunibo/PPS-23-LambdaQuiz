package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.time.LocalDateTime
import java.util.UUID
import scala.util.Try

/** Rappresenta una partita tra più giocatori.
  *
  * @param players
  *   Lista dei giocatori che partecipano alla partita
  * @param completed
  *   booleano che indica se la partita è completata
  * @param lastUpdate
  *   data dell'ultimo aggiornamento della partita
  * @param categories
  *   lista delle categorie di domande che verranno utilizzate nella partita
  * @param id
  *   identificativo univoco della partita
  */
case class Game(
    players: List[User],
    var completed: Boolean,
    var lastUpdate: LocalDateTime,
    categories: List[Category],
    id: String
)

/** Companion object per la classe [[Game]].
  *
  * Abilita la conversione da e verso BSONDocument in maniera trasparente, sfruttando il meccanismo degli impliciti.
  */
object Game {
  def apply(
    players: List[User],
    completed: Boolean,
    lastUpdate: LocalDateTime,
    categories: List[Category],
    id: Option[String] = None
  ): Game =
    Game(players, completed, lastUpdate, categories, id.getOrElse(UUID.randomUUID().toString))

  implicit object GameReader extends BSONDocumentReader[Game]:
    /** Converte un documento BSON in un oggetto di tipo [[Game]].
      *
      * @param doc
      *   il documento BSON da convertire
      * @return
      *   l'oggetto di tipo [[Game]] corrispondente al documento BSON
      */
    def readDocument(doc: BSONDocument): Try[Game] =
      for
        id <- doc.getAsTry[String]("_id")
        players <- doc.getAsTry[List[User]]("players")
        completed <- doc.getAsTry[Boolean]("completed")
        lastUpdate <- doc.getAsTry[LocalDateTime]("lastUpdate")
        categories <- doc.getAsTry[List[String]]("categories")
      yield Game(players, completed, lastUpdate, categories.map(Category.valueOf), id)

  implicit object GameWriter extends BSONDocumentWriter[Game]:
    /** Converte un oggetto di tipo [[Game]] in un documento BSON.
      *
      * @param game
      *   l'oggetto di tipo [[Game]] da convertire
      * @return
      *   il documento BSON corrispondente all'oggetto di tipo [[Score]]
      */
    override def writeTry(game: Game): Try[BSONDocument] =
      for
        id <- Try(game.id)
        players <- Try(game.players)
        completed <- Try(game.completed)
        lastUpdate <- Try(game.lastUpdate)
        categories <- Try(game.categories.map(_.toString))
      yield BSONDocument(
        "_id" -> id,
        "players" -> players,
        "completed" -> completed,
        "lastUpdate" -> lastUpdate,
        "categories" -> categories
      )
}
