package it.unibo.pps.business

import it.unibo.pps.model.User
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserRepository extends Repository[User]:
  override val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase().map(_.collection("users"))
  
  def getUserByLogin(user: User): Future[Option[User]] =
    readOne(
      BSONDocument(
        "username" -> user.username,
        "password" -> user.password
      )
    )

  def getUserByUsername(username: String): Future[Option[User]] =
    readOne(BSONDocument(
      "username" -> username
    ))

end UserRepository
