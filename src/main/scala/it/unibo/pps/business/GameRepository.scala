package it.unibo.pps.business

import it.unibo.pps.model.{Game, User}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONArray, BSONDocument}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class GameRepository extends Repository[Game]:
  protected val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase.map(_.collection("games"))

  override def create(game: Game): Future[Unit] =
    this.collection
      .map(_.insert.one(game))

  override def read(id: String): Future[Option[Game]] =
    this.collection
      .map(
        _.find(
          BSONDocument(
            "_id" -> id
          )
        ).one[Game]
      )
      .flatMap(_.andThen {
        case Failure(e)       => e.printStackTrace()
        case Success(Some(u)) => Some(u)
        case Success(None)    => None
      })

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

  def getCurrentGameFromPlayers(players: List[User]): Future[Option[Game]] =
    this.collection
      .map(
        _.find(
          BSONDocument(
            "$and" -> BSONArray(
              "completed" -> false,
              BSONDocument(
                "$and" -> BSONArray(
                  players.zipWithIndex.map { case (user: User, index) =>
                    BSONDocument(s"user$index" -> user.getID)
                  }
                )
              )
            )
          )
        ).one[Game]
      )
      .flatMap(_.andThen {
        case Failure(e)             => e.printStackTrace()
        case Success(Some(g: Game)) => Some(g)
        case Success(None)          => None
      })

end GameRepository
