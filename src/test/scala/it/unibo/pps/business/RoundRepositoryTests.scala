package it.unibo.pps.business

import it.unibo.pps.model.{Category, Game, Round, User}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import reactivemongo.api.bson.BSONDocument

import java.time.LocalDateTime

class RoundRepositoryTests extends AsyncFlatSpec with should.Matchers:

  private val roundRepository = new RoundRepository
  private val game = new Game(
    new User("user1", "pwd"),
    new User("user2", "pwd"),
    true,
    LocalDateTime.now(),
    List(new Category("categoria 1"), new Category("categoria 2"), new Category("categoria 3"))
  )
  private val round = new Round(game.getID, 3, 0, 1)

  "A round" should "eventually be inserted in the database" in {
    roundRepository
      .create(round)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    roundRepository.readById(round.getID).map(_.get.relatedGameID should be(game.getID))
  }

end RoundRepositoryTests
