package it.unibo.pps.controller

import it.unibo.pps.business.GameRepository
import it.unibo.pps.model.Category.{Geografia, Scienze, Storia}
import it.unibo.pps.model.{Game, User}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*
import scala.concurrent.ExecutionContext.Implicits.global

import java.time.LocalDateTime

class GameControllerTests extends AnyFlatSpec with should.Matchers:

  private val user1 = new User("user1", "PASSWORDDD")
  private val user2 = new User("user2", "PASSWORDDD")
  private val game = new Game(List(user1, user2),
    false, LocalDateTime.now(), List(Storia, Geografia, Scienze))
  private val gameRepository = new GameRepository

  "GameController" should "be able to create a new game" in {
    UserController.checkLogin(List(user1, user2))
    GameController.createNewGame()
    gameRepository.readById(game.getID)
      .map(_.exists(_.getID == game.getID) should be(true))
  }

  "GameController" should "be able to read games of a user" in {
    val games = GameController.getCurrentGamesFromSinglePlayer(user1).orNull
    games != null && games.nonEmpty
  }

  "GameController" should "be able to read a current game" in {
    val game = GameController.getCurrentGameFromPlayers(List(user1, user2)).orNull
    game != null
  }

end GameControllerTests
