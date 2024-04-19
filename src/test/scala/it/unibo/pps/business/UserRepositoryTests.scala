package it.unibo.pps.business

import it.unibo.pps.TestDataInitializer.{players, userRepository}
import it.unibo.pps.model.User
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import org.scalatest.DoNotDiscover

import scala.language.postfixOps

@DoNotDiscover
class UserRepositoryTests extends AsyncFlatSpec with should.Matchers:
  
  private val user = players.head

  "A user" should "be read from the database by its id" in {
    userRepository
      .readById(user.id)
      .map(_.exists(_.username == user.username) should be(true))
  }

  it should "be read from the database by its username" in {
    userRepository
      .getUserByUsername(user.username)
      .map(_.exists(_.id == user.id) should be(true))
  }

  it should "be read from the database by username and password" in {
    userRepository
      .getUserByLogin(user)
      .map(_.exists(_.id == user.id) should be(true))
  }

end UserRepositoryTests
