package it.unibo.pps.business

import it.unibo.pps.TestDataInitializer.{categories, game, gameRepository, players}
import it.unibo.pps.model.Game
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

class GameRepositoryTests extends AsyncFlatSpec with should.Matchers with ScalaFutures:

  "A game" should "be read from the database" in {
    whenReady(gameRepository.readById(game.id)) { g =>
      g.exists(_.id == game.id) should be(true)
    }
  }

  it should "contains both the inserted users" in {
    println("Players: " + players)
    gameRepository
      .readById(game.id)
      .map(
        _.map(players should contain theSameElementsInOrderAs _.players).getOrElse(fail("Game not found"))
      )
  }

  it should "contains all the inserted categories" in {
    gameRepository
      .readById(game.id)
      .map(_.map(_.categories should contain theSameElementsInOrderAs categories).getOrElse(fail("Game not found")))
  }

end GameRepositoryTests
