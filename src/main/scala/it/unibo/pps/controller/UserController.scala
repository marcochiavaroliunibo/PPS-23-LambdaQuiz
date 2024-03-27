package it.unibo.pps.controller

import it.unibo.pps.business.UserRepository
import it.unibo.pps.model.User

import scala.concurrent.Await
import scala.concurrent.duration.*

object UserController:
  
  private val userRepository = new UserRepository
  private var _loggedUsers: Option[List[User]] = None

  def loggedUsers: Option[List[User]] = _loggedUsers

  /** registra un nuovo utente */
  def registerUser(user: User): Unit =
    Await.result(userRepository.create(user), 5.seconds)

  /** verifica che tutti gli utenti passati in input abbiano inserito le credenziali di accesso esatte */
  def checkLogin(users: List[User]): Boolean =
    val foundUsers = users.map(u => Await.result(userRepository.getUserByLogin(u), 5.seconds))
    _loggedUsers = foundUsers.count(_.isEmpty) match
      case 0 => Some(foundUsers.map(_.get))
      case _ => None
    _loggedUsers.nonEmpty

  /** verifica che l'username dato in input è già utilizzato */
  def checkUsername(username: String): Boolean =
    Await.result(userRepository.getUserByUsername(username), 5.seconds).isDefined

end UserController
