package it.unibo.pps.business

import it.unibo.pps.business.{ConnectionMongoDB, UserRepository}
import it.unibo.pps.model.User
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

class UserRepositoryTests extends AsyncFlatSpec with should.Matchers:

  val user = new User("albisyx", "Password!")
  "A user" should "eventually be inserted in the database" in {
    val userRepository = new UserRepository
    userRepository
      .create(user)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    val userRepository = new UserRepository
    val futureUser = userRepository.read(user.getID)
    futureUser
      .map(_.exists(_.getUsername == user.getUsername) should be(true))
  }

  it should "be read from the database by nickname and password" in {
    val userRepository = new UserRepository
    val futureUser = userRepository.getUserByLogin(user)
    futureUser
      .map(_.exists(_.getUsername == user.getUsername) should be(true))
  }

end UserRepositoryTests
