package it.unibo.pps.business

import it.unibo.pps.TestDataInitializer.{gameRepository, games, players}
import it.unibo.pps.model.Game
import org.scalatest.DoNotDiscover
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

@DoNotDiscover
/** Classe di test per [[GameRepository]] */
class GameRepositoryTests extends AsyncFlatSpec with should.Matchers with ScalaFutures:

  @SuppressWarnings(Array("org.wartremover.warts.IterableOps"))
  val game: Game = games.filter(_.completed).head

  "A game" should "be read from the database" in {
    gameRepository
      .readById(game.id)
      .map(_.exists(_.id == game.id) should be(true))
  }

  it should "contains both the inserted users" in {
    gameRepository
      .readById(game.id)
      .map(
        _.map(players should contain theSameElementsInOrderAs _.players).getOrElse(fail("Game not found"))
      )
  }

  it should "contains all the inserted categories" in {
    gameRepository
      .readById(game.id)
      .map(
        _.map(_.categories should contain theSameElementsInOrderAs game.categories).getOrElse(fail("Game not found"))
      )
  }

end GameRepositoryTests
