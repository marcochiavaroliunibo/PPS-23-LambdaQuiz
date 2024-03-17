package it.unibo.pps.business

import it.unibo.pps.business.{ConnectionMongoDB, GameRepository}
import it.unibo.pps.model.{Game, User}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

import java.time.LocalDateTime
import java.util.Date

class GameRepositoryTests extends AsyncFlatSpec with should.Matchers:
 
  val game = new Game(new User("user1", "pwd"), new User("user2", "pwd"), false, LocalDateTime.now())
  "A game" should "eventually be inserted in the database" in {
    val gameRepository = new GameRepository
    gameRepository
      .create(game)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    val gameRepository = new GameRepository
    val futureGame = gameRepository.read(game.getID)
    futureGame
      .map(_.exists(_.getCompleted == game.getCompleted) should be(true))
  }
end GameRepositoryTests
