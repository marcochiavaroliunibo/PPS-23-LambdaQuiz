package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.util.UUID
import scala.util.Try

/** Rappresenta un utente all'interno del sistema.
  * @param username
  *   Nome utente
  * @param password
  *   Password utente
  * @param id
  *   Identificativo univoco dell'utente
  */
case class User(username: String, password: String, id: String)

/** Companion object per la classe [[User]].
  *
  * Abilita la conversione da e verso BSONDocument in maniera trasparente, sfruttando il meccanismo degli impliciti.
  */
object User {
  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
  def apply(username: String, password: String, id: Option[String] = None): User =
    User(username, password, id.getOrElse(UUID.randomUUID().toString))

  implicit object UserReader extends BSONDocumentReader[User]:
    /** Converte un documento BSON in un oggetto di tipo [[User]].
      * @param doc
      *   il documento BSON da convertire
      * @return
      *   l'oggetto [[User]] corrispondente al documento BSON
      */
    def readDocument(doc: BSONDocument): Try[User] =
      for
        id <- doc.getAsTry[String]("_id")
        username <- doc.getAsTry[String]("username")
        password <- doc.getAsTry[String]("password")
      yield User(username, password, id)

  implicit object UserWriter extends BSONDocumentWriter[User]:
    /** Converte un oggetto di tipo [[User]] in un documento BSON.
      * @param user
      *   l'oggetto [[User]] da convertire
      * @return
      *   il documento BSON corrispondente all'oggetto di tipo [[User]]
      */
    def writeTry(user: User): Try[BSONDocument] =
      for
        id <- Try(user.id)
        username <- Try(user.username)
        password <- Try(user.password)
      yield BSONDocument("_id" -> id, "username" -> username, "password" -> password)
}
