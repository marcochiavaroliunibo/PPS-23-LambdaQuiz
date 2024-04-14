package it.unibo.pps.controller

import it.unibo.pps.business.UserRepository
import it.unibo.pps.model.User

import scala.concurrent.Await
import scala.concurrent.duration.*

/** Controller che gestisce le operazioni relative agli utenti */
object UserController:

  private val userRepository = new UserRepository

  /** listra di utenti autenticati */
  private var _loggedUsers: Option[List[User]] = None

  def loggedUsers: Option[List[User]] = _loggedUsers

  /** Metodo bloccante che registra un utente nel sistema
    * @param user
    *   utente da registrare
    */
  def registerUser(user: User): Unit =
    Await.result(userRepository.create(user), 5.seconds)

  /** Metodo bloccante che verifica se le credenziali fornite sono corrette.
    * @param users
    *   lista di utenti dei quali verificare le credenziali
    * @return
    *   [[true]] se le credenziali sono corrette, [[false]] altrimenti
    */
  def authenticateUsers(users: List[User]): Boolean =
    val foundUsers = users.map(u => Await.result(userRepository.getUserByLogin(u), 5.seconds))
    _loggedUsers = foundUsers.count(_.isEmpty) match
      case 0 => Some(foundUsers.map(_.get))
      case _ => None
    _loggedUsers.nonEmpty

  /** Metodo bloccante che verifica se l'username è già in uso.
    * @param username
    *   username da verificare
    * @return
    *   [[true]] se l'username è già in uso, [[false]] altrimenti
    */
  def isUsernameAlreadyInUse(username: String): Boolean =
    Await.result(userRepository.getUserByUsername(username), 5.seconds).isDefined

end UserController
