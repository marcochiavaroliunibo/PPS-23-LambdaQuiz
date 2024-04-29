package it.unibo.pps.business

import it.unibo.pps.model.User
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import java.util.concurrent.Executors.newSingleThreadExecutor
import scala.concurrent.{ExecutionContext, Future}

/** Classe che rappresenta il repository per l'entità [[User]].
  *
  * Fornisce metodi per l'interazione con il database.
  */
class UserRepository extends Repository[User]:
  
  override val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase().map(_.collection("users"))

  /** Metodo che permette di ottenere un utente dal database in base al suo username e password.
    *
    * Viene utilizzato per effettuare il login.
    *
    * @param user
    *   utente da cercare di tipo [[User]]
    * @return
    *   l'utente trovato nel database come [[Option]] di [[User]]
    */
  def getUserByLogin(user: User): Future[Option[User]] =
    readOne(
      BSONDocument(
        "username" -> user.username,
        "password" -> user.password
      )
    )

  /** Metodo che permette di ottenere un utente dal database in base al suo username.
    * @param username
    *   username dell'utente da cercare
    * @return
    *   l'utente trovato nel database come [[Option]] di [[User]]
    */
  def getUserByUsername(username: String): Future[Option[User]] =
    readOne(
      BSONDocument(
        "username" -> username
      )
    )

end UserRepository
