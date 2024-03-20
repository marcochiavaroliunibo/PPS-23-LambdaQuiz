package it.unibo.pps.controller

import it.unibo.pps.business.UserRepository
import it.unibo.pps.model.User

import scala.concurrent.Await
import scala.concurrent.duration.*

object UserController:
  
  private val userRepository = new UserRepository
  private var _loggedUsers: Option[List[User]] = None

  def loggedUsers: Option[List[User]] = _loggedUsers

  def registerUser(user: User): Unit =
    Await.result(userRepository.create(user), 5.seconds)

  def checkLogin(users: List[User]): Boolean =
    val foundUsers = users.filter(u => Await.result(userRepository.getUserByLogin(u), 5.seconds).isDefined)
    _loggedUsers = foundUsers match
      case l if l.size == 2 => Some(l)
      case _                => None
    _loggedUsers.nonEmpty

  def checkUsername(username: String): Boolean =
    Await.result(userRepository.getUserByUsername(username), 5.seconds).isDefined

end UserController
