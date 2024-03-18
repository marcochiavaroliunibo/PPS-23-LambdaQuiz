package it.unibo.pps.controller

import it.unibo.pps.model.User
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

class UserControllerTests extends AsyncFlatSpec with should.Matchers:

  "UserController" should "create and login by Controller" in {
    val user = new User("Marco", "PWD!!")
    UserController.createUser(user)
    UserController.checkLogin(List(user)) should be(true)
  }

end UserControllerTests
