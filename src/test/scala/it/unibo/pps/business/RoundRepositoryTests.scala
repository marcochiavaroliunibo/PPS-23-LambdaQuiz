package it.unibo.pps.business

import it.unibo.pps.TestDataInitializer.{game, round, roundRepository}
import it.unibo.pps.model.Round
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import reactivemongo.api.bson.BSONDocument

class RoundRepositoryTests extends AsyncFlatSpec with should.Matchers:

  "A round" should "be read from the database by its related game's ID" in {
    roundRepository
      .readOne(BSONDocument("relatedGameID" -> game.id))
      .map(_.exists(_.id == round.id) should be(true))
  }

end RoundRepositoryTests
