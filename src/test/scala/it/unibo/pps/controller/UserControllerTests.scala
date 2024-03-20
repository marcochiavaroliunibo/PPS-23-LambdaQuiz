package it.unibo.pps.controller

import it.unibo.pps.model.User
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

class UserControllerTests extends AsyncFlatSpec with should.Matchers:

  private val users: List[User] = (1 to 2).map(i => new User(s"UserTest$i", s"password$i")).toList

  "UserController" should "be able to register some users" in {
    users.map(UserController.registerUser) shouldBe a[List[Unit]]
  }

  it should "be able to log in users" in {
    UserController.checkLogin(users) should be(true)
  }

end UserControllerTests
