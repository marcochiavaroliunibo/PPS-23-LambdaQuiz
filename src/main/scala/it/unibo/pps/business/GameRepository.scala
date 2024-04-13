package it.unibo.pps.business

import it.unibo.pps.model.{Game, User}
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/** Classe che rappresenta il repository per l'entità [[Game]].
  *
  * Fornisce metodi più specifici per l'interazione con il database.
  */
class GameRepository extends Repository[Game]:

  override val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase().map(_.collection("games"))

    /** Metodo che permette di ottenere una partita in corso a partire dalla lista dei suoi giocatori.
        *
        * @param players lista di giocatori
        * @return una lista di partite in corso
        */
  def getCurrentGameFromPlayers(players: List[User]): Future[Option[List[Game]]] =
    val query = BSONDocument(
      "completed" -> false,
      "players" -> BSONDocument("$all" -> players)
    )
    val sort = BSONDocument("lastUpdate" -> -1)
    readMany(query, sort)

  /**
   * Metodo che permette di ottenere l'ultima partita completata da un utente.
   * @param user utente
   * @param maxDocs numero massimo di documenti da restituire
   * @return una lista di partite completate
   */
  def getLastGameCompletedByUser(user: User, maxDocs: Int): Future[Option[List[Game]]] =
    val query = BSONDocument(
      "completed" -> true,
      "players" -> BSONDocument("$eq" -> user)
    )
    val sort = BSONDocument("lastUpdate" -> -1)
    readMany(query, sort, maxDocs)

end GameRepository
