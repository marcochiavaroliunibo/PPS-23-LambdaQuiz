package it.unibo.pps.controller

import it.unibo.pps.TestDataInitializer.{games, rounds}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*
import org.scalatest.DoNotDiscover

@DoNotDiscover
class RoundControllerTests extends AnyFlatSpec with should.Matchers:

  private val randomGame = games.last
  private val allRoundOfAGame = rounds.filter(_.gameId == randomGame.id)

  private val statsOfUser = randomGame.players.last
  
  "RoundController" should "be able to compute points correctly" in {
    val computedScore = allRoundOfAGame
      .flatMap(_.scores)
      .filter(_.user == statsOfUser)
      .map(_.score)
      .sum
    RoundController.computePartialPointsOfUser(statsOfUser, randomGame) should be(computedScore)
  }

end RoundControllerTests
