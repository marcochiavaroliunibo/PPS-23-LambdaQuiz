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
  private val ROUND_FOR_GAME: Int = 3

  def getLastRoundByGame: Round = {
    val roundResult = Await.result(roundRepository.getAllRoundsByGame(gameOfLoggedUsers.get), 5.seconds)
    if roundResult.isDefined then roundResult.get.last else null
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
    if (RoundController.getRound.numberRound == ROUND_FOR_GAME && RoundController.getRound.scores.forall(_.score != -1))
      val gameEdited: Game = gameOfLoggedUsers.get
      gameEdited.completed = true
      gameEdited.lastUpdate = LocalDateTime.now()
      gameRepository.update(gameEdited, gameEdited.getID)
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
