package it.unibo.pps.business

import it.unibo.pps.business.{ConnectionMongoDB, Repository}
import it.unibo.pps.model.{Game, Round}
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class RoundRepository extends Repository[Round]:
  protected val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase.map(_.collection("rounds"))

  def update(round: Round) : Future[Unit] =
    this.collection
      .map(_.findAndUpdate(BSONDocument(
        "_id" -> round.getID
      ), round))

  // todo: caso da capire: come gestire ordinamento
  def getLastRoundByGame(game: Game): Future[Option[Round]] =
    this.collection
      .map(
        _.find(
          BSONDocument(
            "game" -> game.getID
          )
        ).sort(BSONDocument("numberRound" -> -1)).one[Round]
      )
      .flatMap(_.andThen {
        case Failure(e) => e.printStackTrace()
        case Success(Some(u)) => Some(u)
        case Success(None) => None
      })
  
  def getAllRoundsByGame(game: Game): Future[Option[List[Round]]] =
    readMany(BSONDocument(
      "game" -> game.getID
    ))
    

end RoundRepository
