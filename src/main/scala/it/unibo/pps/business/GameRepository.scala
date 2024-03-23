package it.unibo.pps.business

import it.unibo.pps.model.{Game, User}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONArray, BSONDocument}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GameRepository extends Repository[Game]:
  override val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase.map(_.collection("games"))

  def getCurrentGameFromPlayers(players: List[User]): Future[Option[Game]] =
    val query = BSONDocument(
      "completed" -> false,
      "players" -> BSONDocument("$elemMatch" -> BSONDocument("_id" -> BSONDocument("$in" -> players.map(_.getID))))
    )
    readOne(query)
  
  def getLastGameCompletedByUser(user: User): Future[Option[List[Game]]] =
    val query = BSONDocument(
      "completed" -> true,
      "players" -> BSONDocument("$elemMatch" -> BSONDocument("_id" -> BSONDocument("$in" -> user.getID)))
    )
    readMany(query)

end GameRepository
