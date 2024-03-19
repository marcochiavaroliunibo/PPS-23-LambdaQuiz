package it.unibo.pps.controller

import it.unibo.pps.business.{GameRepository, RoundRepository}
import it.unibo.pps.model.{Game, User}

import scala.concurrent.Await
import scala.concurrent.duration.*

object GameController:

  private var _gameOfLoggedUsers: Option[Game] = None
  private val gameRepository = new GameRepository
  private val roundRepository = new RoundRepository

//  def getLastRoundByGame: Round = {
//    val roundResult = Await.result(roundRepository.getLastRoundByGame(game), 5.seconds)
//    if roundResult.isDefined then roundResult.get else null
//  }

  def gameOfLoggedUsers: Option[Game] = _gameOfLoggedUsers
  private def gameOfLoggedUsers_=(g: Game): Unit = _gameOfLoggedUsers = Some(g)

  def getCurrentGameFromPlayers(users: List[User]): Option[Game] =
    gameOfLoggedUsers match
      case Some(g: Game) => Some(g)
      case None =>
        Await.result(gameRepository.getCurrentGameFromPlayers(users), 5.seconds) match
          case Some(g) =>
            gameOfLoggedUsers = g
            Some(g)
          case None => None

  //  def computePartialPointsOfUser(u: User): Int =
//    Await.result(roundRepository.getAllRoundsByGame(gameOfLoggedUsers.get), 5.seconds).

end GameController
