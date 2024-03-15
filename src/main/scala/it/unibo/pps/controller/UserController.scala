package it.unibo.pps.controller

import scala.concurrent.ExecutionContext.Implicits.global

import it.unibo.pps.Main.category
import it.unibo.pps.business.UserRepository
import it.unibo.pps.model.User

class UserController:
  
  private val userRepository = new UserRepository
  
  def checkLogin (users: List[User]) : Boolean = {
    var statusLogin: Boolean = true
    users.foreach(user => {
      userRepository.getUserByLogin(user)foreach(value => if(value == None) then statusLogin = false)
    })
    statusLogin
  }
  
end UserController
