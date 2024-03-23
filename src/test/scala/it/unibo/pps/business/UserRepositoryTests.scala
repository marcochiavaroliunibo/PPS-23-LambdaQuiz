package it.unibo.pps.business

import it.unibo.pps.model.User
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

import scala.language.postfixOps

class UserRepositoryTests extends AsyncFlatSpec with should.Matchers:
  private val user = new User("UserTest", "passwordfdd")
  private val userRepository = new UserRepository

  "A user" should "eventually be inserted in the database" in {
    userRepository.create(user).map(_ shouldBe a[Unit])
  }

  it should "be read from the database by id" in {
    userRepository
      .readById(user.getID)
      .map(_.exists(_.username == user.username) should be(true))
  }

  it should "be read from the database by username and password" in {
    userRepository
      .getUserByLogin(user)
      .map(_.exists(_.username == user.username) should be(true))
  }

end UserRepositoryTests
