package it.unibo.pps.controller

import it.unibo.pps.TestDataInitializer.players
import it.unibo.pps.model.User
import org.scalatest.DoNotDiscover
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*

@DoNotDiscover
class UserControllerTests extends AnyFlatSpec with should.Matchers:

  private val unregisteredUsers = players.map(u => User(u.password, u.username))

  "UserController" should "not let users to register if they have an already registered username" in {
    players.map(u => UserController.isUsernameAlreadyInUse(u.username)).forall(b => b) should be(true)
  }

  it should "not let users to log in if they are both unregistered" in {
    UserController.authenticateUsers(unregisteredUsers) should be(false)
  }

  it should "not let users to log in if one of them is unregistered" in {
    @SuppressWarnings(Array("org.wartremover.warts.IterableOps"))
    val notAllRegisteredUsers = players.head :: unregisteredUsers.take(1)
    UserController.authenticateUsers(notAllRegisteredUsers) should be(false)
  }

  it should "let registered users to log in" in {
    UserController.authenticateUsers(players) should be(true)
  }

end UserControllerTests
