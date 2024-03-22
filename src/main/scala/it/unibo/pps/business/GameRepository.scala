package it.unibo.pps.business

import it.unibo.pps.model.{Game, User}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONArray, BSONDocument}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class GameRepository extends Repository[Game]:
  override val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase.map(_.collection("games"))

  def getCurrentGameFromPlayers(players: List[User]): Future[Option[Game]] =
    readOne(
      BSONDocument(
        "completed" -> false,
        "user1" -> players.head.getID,
        "user2" -> players.last.getID
      )
    )

  // mi sa che non ci serve questo metodo
  def getGameInProgressByUser(user: User): Future[Option[List[Game]]] =
    readMany(BSONDocument(
      "$and" -> BSONArray(
        "completed" -> false,
        BSONDocument(
          "$or" -> BSONArray(
            BSONDocument("user1" -> user.getID),
            BSONDocument("user2" -> user.getID)
          )
        )
      )
    ))

  def getLastGameCompletedByUser(user: User): Future[Option[List[Game]]] =
    readMany(BSONDocument(
      "$and" -> BSONArray(
        "completed" -> true,
        BSONDocument(
          "$or" -> BSONArray(
            BSONDocument("user1" -> user.getID),
            BSONDocument("user2" -> user.getID)
          )
        )
      )
    ))

  def update(game: Game): Future[Unit] =
    this.collection
      .map(_.findAndUpdate(BSONDocument(
        "_id" -> game.getID
      ), game))

end GameRepository
