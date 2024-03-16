package it.unibo.pps.business

import it.unibo.pps.business.{ConnectionMongoDB, RoundRepository}
import it.unibo.pps.model.{Category, Game, Round, User}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

import java.time.LocalDateTime

class RoundRepositoryTests extends AsyncFlatSpec with should.Matchers:
  "The application" should "connect to the database" in {
    ConnectionMongoDB.getDatabase.map(db => db.name shouldEqual "LambdaQuiz")
  }

  val game = new Game(new User("user1", "pwd"), new User("user2", "pwd"), true, LocalDateTime.now())
  val round = new Round(game, 3, 0, 1)
  "A round" should "eventually be inserted in the database" in {
    val roundRepository = new RoundRepository
    roundRepository
      .create(round)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    val roundRepository = new RoundRepository
    val futureRound = roundRepository.read(round.getID)
    futureRound
      .map(_.exists(_.getNumberRound == round.getNumberRound) should be(true))
  }
end RoundRepositoryTests