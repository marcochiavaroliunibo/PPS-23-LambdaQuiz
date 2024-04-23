package it.unibo.pps.business

import it.unibo.pps.TestDataInitializer.{games, roundRepository, rounds}
import it.unibo.pps.model.{Game, Round}
import org.scalatest.DoNotDiscover
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import reactivemongo.api.bson.BSONDocument

@DoNotDiscover
class RoundRepositoryTests extends AsyncFlatSpec with should.Matchers:

  val game: Game = games.last
  val allRounds: List[Round] = rounds.filter(_.gameId == game.id)

  "A round" should "be read from the database by its related game's ID" in {
    roundRepository.readOne(BSONDocument("gameId" -> game.id)).map(_.isDefined shouldEqual true)
  }

  "Rounds of a game" should "be read from the database by the belonging game" in {
    roundRepository
      .getAllRoundsByGame(game)
      .map(
        _.map(_ should contain theSameElementsAs allRounds)
          .getOrElse(fail("No rounds found"))
      )
  }

end RoundRepositoryTests
