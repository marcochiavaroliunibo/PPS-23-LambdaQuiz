package it.unibo.pps.business

import it.unibo.pps.TestDataInitializer.{players, userRepository}
import it.unibo.pps.model.User
import org.scalatest.DoNotDiscover
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

import scala.language.postfixOps

@DoNotDiscover
/** Classe di test per [[UserRepository]] */
class UserRepositoryTests extends AsyncFlatSpec with should.Matchers:

  @SuppressWarnings(Array("org.wartremover.warts.IterableOps"))
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
