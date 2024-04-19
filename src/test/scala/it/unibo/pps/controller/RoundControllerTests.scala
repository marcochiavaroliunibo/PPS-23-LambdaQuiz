package it.unibo.pps.controller

import it.unibo.pps.TestDataInitializer.{games, rounds}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*
import org.scalatest.DoNotDiscover

@DoNotDiscover
class RoundControllerTests extends AnyFlatSpec with should.Matchers:

  private val randomGame = games.last
  private val allRoundOfAGame = rounds.filter(_.gameId == randomGame.id)

  "RoundController" should "be able to compute the points correctly" in {
    val computedScore = allRoundOfAGame
      .flatMap(_.scores)
      .filter(_.user == randomGame.players.last)
      .map(_.score)
      .sum
    RoundController.computePartialPointsOfUser(randomGame.players.last, randomGame) should be(computedScore)
  }

end RoundControllerTests
