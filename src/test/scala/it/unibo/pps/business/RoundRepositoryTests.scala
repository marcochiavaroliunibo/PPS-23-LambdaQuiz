package it.unibo.pps.business

import it.unibo.pps.model.{Category, Game, Round, Score, User}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

import java.time.LocalDateTime

class RoundRepositoryTests extends AsyncFlatSpec with should.Matchers:
  private val roundRepository = new RoundRepository
  private val player1: User = new User("user1", "pwd")
  private val player2: User = new User("user2", "pwd")
  private val game = new Game(
    List(player1,player2),
    true,
    LocalDateTime.now(),
    List(Category.Scienze, Category.Storia, Category.Geografia)
  )
  private val round = new Round(game.getID, List(new Score(player1, 3), new Score(player2, 3)), 2)

  "A round" should "eventually be inserted in the database" in {
    roundRepository
      .create(round)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    roundRepository.readById(round.getID).map(_.get._relatedGameID should be(game.getID))
  }

end RoundRepositoryTests
