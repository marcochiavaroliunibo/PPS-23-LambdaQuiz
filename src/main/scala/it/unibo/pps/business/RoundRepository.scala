package it.unibo.pps.business

import it.unibo.pps.model.{Game, Round}
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import java.util.concurrent.Executors.newSingleThreadExecutor
import scala.concurrent.{ExecutionContext, Future}

/** Classe che rappresenta il repository per l'entità [[Round]].
  *
  * Fornisce metodi più specifici per l'interazione con il database.
  */
class RoundRepository extends Repository[Round]:
  
  protected val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase().map(_.collection("rounds"))

  /** Metodo che restituisce tutti i round appartenenti ad un determinato gioco.
    *
    * @param game
    *   il gioco di cui si vogliono recuperare i round
    * @return
    *   una lista di round appartenenti al gioco specificato
    */
  def getAllRoundsByGame(game: Game): Future[Option[List[Round]]] =
    val query = BSONDocument("gameId" -> game.id)
    val sort = BSONDocument("numberRound" -> 1)
    readMany(query, sort)

end RoundRepository
