package it.unibo.pps.business

import it.unibo.pps.business.{ConnectionMongoDB, Repository}
import it.unibo.pps.model.User
import reactivemongo.api.bson.{BSONArray, BSONDocument}
import reactivemongo.api.bson.collection.BSONCollection
import sun.security.util.Password

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class UserRepository extends Repository[User]:
  protected val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase.map(_.collection("users"))

  override def create(user: User): Future[Unit] =
    this.collection
      .map(_.insert.one(user))

  override def read(id: String): Future[Option[User]] =
    this.collection
      .map(
        _.find(
          BSONDocument(
            "_id" -> id
          )
        ).one[User]
      )
      .flatMap(_.andThen {
        case Failure(e)       => e.printStackTrace()
        case Success(Some(u)) => Some(u)
        case Success(None)    => None
      })

  def getUserByLogin(user: User): Future[Option[User]] =
    this.collection
      .map(
        _.find(
          BSONDocument(
            "$and" -> BSONArray(
                BSONDocument("username" -> user.getUsername),
                BSONDocument("password" -> user.getPassword),
              )
            )
        ).one[User]
      )
      .flatMap(_.andThen {
        case Failure(e) => e.printStackTrace()
        case Success(Some(u)) => Some(u)
        case Success(None) => None
      })

end UserRepository
