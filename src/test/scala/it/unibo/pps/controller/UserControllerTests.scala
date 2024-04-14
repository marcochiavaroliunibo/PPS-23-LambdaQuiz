package it.unibo.pps.controller

import it.unibo.pps.model.User
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*

class UserControllerTests extends AnyFlatSpec with should.Matchers:

  private val users: List[User] = (1 to 2).map(i => new User(s"UserTest$i", s"password$i")).toList

  "UserController" should "be able to register some users" in {
    users.map(UserController.registerUser) shouldBe a[List[Unit]]
  }

  it should "not let users to register if they have an already registered username" in {
    users.map(u => UserController.isUsernameAlreadyInUse(u.username)).forall(b => b) should be(true)
  }

  it should "not let users to log in if they are both unregistered" in {
    val unregisteredUsers = users.map(u => new User(u.password, u.username))
    UserController.authenticateUsers(unregisteredUsers) should be(false)
  }

  it should "not let users to log in if one of them is unregistered" in {
    val unregisteredUser = users.map(u => new User(u.password, u.username)).take(1)
    UserController.authenticateUsers(users.head :: unregisteredUser) should be(false)
  }

  it should "let registered users to log in" in {
    UserController.authenticateUsers(users) should be(true)
  }

end UserControllerTests
