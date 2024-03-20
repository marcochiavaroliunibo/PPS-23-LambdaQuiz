package it.unibo.pps.business

import it.unibo.pps.model.{Game, User}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONArray, BSONDocument}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class GameRepository extends Repository[Game]:
  override val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase.map(_.collection("games"))

  override def read(id: String): Future[Option[Game]] = ???

  def getCurrentGameFromPlayers(players: List[User]): Future[Option[Game]] =
    readOne(
      BSONDocument(
        "completed" -> false,
        "user1" -> players.head.getID,
        "user2" -> players.last.getID
      )
    )

  // mi sa che non ci serve questo metodo
  def getGameInProgressByUser(user: User): Future[List[Game]] =
    this.collection
      .map(
        _.find(
          BSONDocument(
            "$and" -> BSONArray(
              "completed" -> false,
              BSONDocument(
                "$or" -> BSONArray(
                  BSONDocument("user1" -> user.getID),
                  BSONDocument("user2" -> user.getID)
                )
              )
            )
          )
        ).cursor[Game]().collect[List]()
      )
      .flatMap(_.andThen {
        case Failure(e)    => e.printStackTrace()
        case Success(List) => List
      })

  def getLastGameCompletedByUser(user: User): Future[List[Game]] =
    this.collection
      .map(
        _.find(
          BSONDocument(
            "$and" -> BSONArray(
              "completed" -> true,
              BSONDocument(
                "$or" -> BSONArray(
                  BSONDocument("user1" -> user.getID),
                  BSONDocument("user2" -> user.getID)
                )
              )
            )
          )
        ).sort(BSONDocument("lastUpdate" -> -1)).cursor[Game]().collect[List]()
      )
      .flatMap(_.andThen {
        case Failure(e)    => e.printStackTrace()
        case Success(List) => List
      })

end GameRepository
