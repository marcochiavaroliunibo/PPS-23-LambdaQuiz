package it.unibo.pps.controller

import scala.concurrent.ExecutionContext.Implicits.global
import it.unibo.pps.business.UserRepository
import it.unibo.pps.model.User

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class UserController:

  private val userRepository = new UserRepository

  def createUser (user: User) : Unit = {
    userRepository.create(user)
  }
  
  def checkLogin (users: List[User]) : Boolean = {
    var statusLogin: Boolean = true
    users.foreach(user => {
      val userResult = Await.result(userRepository.getUserByLogin(user), Duration.Inf)
      if userResult.isEmpty then statusLogin = false
    })
    statusLogin
  }
  
  def checkUsername (username: String) : Boolean = {
    val userResult = Await.result(userRepository.getUserByUsername(username), Duration.Inf)
    if userResult.isDefined then true else false
  }

end UserController
