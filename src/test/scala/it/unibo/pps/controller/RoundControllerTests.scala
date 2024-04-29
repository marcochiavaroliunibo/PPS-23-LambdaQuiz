package it.unibo.pps.controller

import it.unibo.pps.TestDataInitializer.{games, rounds}
import org.scalatest.DoNotDiscover
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*

@DoNotDiscover
@SuppressWarnings(Array("org.wartremover.warts.IterableOps"))
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
    RoundController.computePartialPointsOfUser(statsOfUser, Some(randomGame)) should be(computedScore)
  }

end RoundControllerTests
