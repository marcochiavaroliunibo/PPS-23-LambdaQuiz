package it.unibo.pps.business

import it.unibo.pps.model.{Game, Round}
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class RoundRepository extends Repository[Round]:
  protected val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase().map(_.collection("rounds"))

  def getAllRoundsByGame(game: Game): Future[Option[List[Round]]] =
    val query = BSONDocument("gameID" -> game.getID)
    val sort = BSONDocument("numberRound" -> 1)
    readWithSort(query, sort)

end RoundRepository
