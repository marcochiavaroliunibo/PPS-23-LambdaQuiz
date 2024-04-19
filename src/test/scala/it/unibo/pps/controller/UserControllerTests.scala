package it.unibo.pps.controller

import it.unibo.pps.model.User
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*
import org.scalatest.DoNotDiscover

@DoNotDiscover
class UserControllerTests extends AnyFlatSpec with should.Matchers:

  private val testUsers: List[User] = (1 to 2).map(i => User(s"TestUser$i", s"password$i")).toList
  private val unregisteredUsers = testUsers.map(u => User(u.password, u.username))

  "UserController" should "be able to register some users" in {
    testUsers.map(UserController.registerUser) shouldBe a[List[Unit]]
  }

  it should "not let users to register if they have an already registered username" in {
    testUsers.map(u => UserController.isUsernameAlreadyInUse(u.username)).forall(b => b) should be(true)
  }

  it should "not let users to log in if they are both unregistered" in {
    UserController.authenticateUsers(unregisteredUsers) should be(false)
  }

  it should "not let users to log in if one of them is unregistered" in {
    UserController.authenticateUsers(testUsers.head :: unregisteredUsers.take(1)) should be(false)
  }

  it should "let registered users to log in" in {
    UserController.authenticateUsers(testUsers) should be(true)
  }

end UserControllerTests
