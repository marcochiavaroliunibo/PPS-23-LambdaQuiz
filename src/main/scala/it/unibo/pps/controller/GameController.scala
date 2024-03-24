package it.unibo.pps.controller

import it.unibo.pps.business.{GameRepository, RoundRepository}
import it.unibo.pps.model.Category.{CulturaGenerale, Geografia, Scienze}
import it.unibo.pps.model.{Game, Round, User}

import java.time.LocalDateTime
import scala.concurrent.Await
import scala.concurrent.duration.*

object GameController:

  private var _gameOfLoggedUsers: Option[Game] = None
  private val gameRepository = new GameRepository
  private val roundRepository = new RoundRepository

  def getLastRoundByGame: Round = {
    val roundResult = Await.result(roundRepository.getLastRoundByGame(gameOfLoggedUsers.get), 5.seconds)
    if roundResult.isDefined then roundResult.get else null
  }

  def gameOfLoggedUsers: Option[Game] = _gameOfLoggedUsers
  def gameOfLoggedUsers_=(g: Game): Unit = _gameOfLoggedUsers = Some(g)

  def getCurrentGameFromPlayers(users: List[User]): Option[Game] =
    if users.isEmpty then None
    else gameOfLoggedUsers match
      case Some(g: Game) => Some(g)
      case None =>
        Await.result(gameRepository.getCurrentGameFromPlayers(users), 5.seconds) match
          case Some(g) =>
            gameOfLoggedUsers = g
            Some(g)
          case None => None

  def checkFinishGame(): Unit = {
    if (
      RoundController.getRound.numberRound == QuestionController.QUESTION_FOR_ROUND && RoundController.getRound.scores
        .forall(_.score != -1)
    )
      gameOfLoggedUsers.get.completed = true
      gameRepository.update(gameOfLoggedUsers.get, gameOfLoggedUsers.get.getID)
  }

  def createNewMatch(): Unit =
    val newGame = new Game(
      UserController.loggedUsers.getOrElse(List.empty),
      false,
      LocalDateTime.now(),
      List(CulturaGenerale, Scienze, Geografia)
    )
    Await.result(gameRepository.create(newGame), 5.seconds)

end GameController
