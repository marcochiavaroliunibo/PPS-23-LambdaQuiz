package it.unibo.pps.controller

import it.unibo.pps.business.{GameRepository, RoundRepository, UserRepository}
import it.unibo.pps.model.Category.{Geografia, Scienze, Storia}
import it.unibo.pps.model.{Game, Round, Score, User}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*

import java.time.LocalDateTime
import scala.concurrent.ExecutionContext.Implicits.global

class RoundControllerTests extends AnyFlatSpec with should.Matchers:

  private val user1 = new User("user1", "PASSWORDDD")
  private val user2 = new User("user2", "PASSWORDDD")
  private val game = new Game(List(user1, user2),
    false, LocalDateTime.now(), List(Storia, Geografia, Scienze))
  private val round = new Round(game.getID, List(new Score(user1), new Score(user2)), 1)

  private val gameRepository = new GameRepository
  private val userRepository = new UserRepository
  private val roundRepository = new RoundRepository

  "RoundController" should "be able to create a new round" in {
    userRepository.create(user1)
    userRepository.create(user2)
    UserController.checkLogin(List(user1, user2))
    GameController.createNewGame()
    RoundController.createRound(round)
    roundRepository.readById(round.getID)
      .map(_.exists(_.getID == round.getID) should be(true))
  }

  "RoundController" should "be able to read the points" in {
    RoundController.computePartialPointsOfUser(user1, game) == 0 should be(true)
  }

  "RoundController" should "be able to read all rounds of the game" in {
    val rounds = RoundController.getAllRoundByGame(game)
    rounds.length == 1 && rounds.head.getID == round.getID should be(true)
  }

end RoundControllerTests
