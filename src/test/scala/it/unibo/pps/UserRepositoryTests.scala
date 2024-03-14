package it.unibo.pps

import it.unibo.pps.business.{ConnectionMongoDB, UserRepository}
import it.unibo.pps.model.User
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

class UserRepositoryTests extends AsyncFlatSpec with should.Matchers:
  "The application" should "connect to the database" in {
    ConnectionMongoDB.getDatabase.map(db => db.name shouldEqual "LambdaQuiz")
  }

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
      .map(_.exists(_.getName == user.getName) should be(true))
  }
end UserRepositoryTests
