package it.unibo.pps.business

import it.unibo.pps.model.{Category, Game, User}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import reactivemongo.api.bson.BSONDocument

import java.time.LocalDateTime

class GameRepositoryTests extends AsyncFlatSpec with should.Matchers:

  private val gameRepository = new GameRepository
  private val game = new Game(
    new User("user1", "pwd"),
    new User("user2", "pwd"),
    false,
    LocalDateTime.now(),
    List(new Category("categoria 1"), new Category("categoria 2"), new Category("categoria 3"))
  )

  "A game" should "eventually be inserted in the database" in {
    gameRepository
      .create(game)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    val idQuery = BSONDocument("_id" -> game.getID)
    gameRepository
      .readOne(idQuery)
      .map(_.exists(_.getID == game.getID) should be(true))
  }

end GameRepositoryTests
