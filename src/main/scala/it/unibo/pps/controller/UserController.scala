package it.unibo.pps.controller

import it.unibo.pps.business.UserRepository
import it.unibo.pps.model.User

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object UserController:

  private val userRepository = new UserRepository
  private var loggedUsers: List[User] = List.empty

  def createUser(user: User): Unit = {
    userRepository.create(user)
  }

  def checkLogin(users: List[User]): Boolean = {
    var statusLogin: Boolean = true
    users.foreach(user => {
      val userResult = Await.result(userRepository.getUserByLogin(user), Duration.Inf)
      if userResult.isEmpty then statusLogin = false
    })
    loggedUsers = users
    statusLogin
  }

  def checkUsername(username: String): Boolean = {
    val userResult = Await.result(userRepository.getUserByUsername(username), Duration.Inf)
    if userResult.isDefined then true else false
  }

  def getLoggedUsers: Option[List[User]] = Option(loggedUsers)

end UserController
