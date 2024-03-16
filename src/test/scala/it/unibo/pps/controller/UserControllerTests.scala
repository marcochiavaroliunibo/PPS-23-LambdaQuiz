package it.unibo.pps.controller

import it.unibo.pps.controller.UserController
import it.unibo.pps.model.User
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

class UserControllerTests extends AsyncFlatSpec with should.Matchers:

  it should "create and login by Controller" in {
    val userController = new UserController
    val user = new User("Marco", "PWD!!")
    userController.createUser(user)
    userController.checkLogin(List(user)) should be(true)
  }

end UserControllerTests
