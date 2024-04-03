package it.unibo.pps.business

import it.unibo.pps.model.{Game, User}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONArray, BSONDocument}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GameRepository extends Repository[Game]:
  override val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase().map(_.collection("games"))

  def getCurrentGameFromPlayers(players: List[User]): Future[Option[List[Game]]] =
    val query = BSONDocument(
      "completed" -> false,
      "players" -> BSONDocument("$all" -> players)
    )
    val sort = BSONDocument("lastUpdate" -> -1)
    readWithSort(query, sort)

  def getLastGameCompletedByUser(user: User, maxDocs: Int = Int.MaxValue): Future[Option[List[Game]]] =
    val query = BSONDocument(
      "completed" -> true,
      "players" -> BSONDocument("$eq" -> user)
    )
    val sort = BSONDocument("lastUpdate" -> -1)
    readWithSort(query, sort, maxDocs)

end GameRepository
