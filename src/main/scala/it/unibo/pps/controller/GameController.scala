package it.unibo.pps.controller

import it.unibo.pps.business.RoundRepository
import it.unibo.pps.model.{Game, Round, User}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object GameController:

  private var game: Game = null
  private val roundRepository = new RoundRepository

  def getLastRoundByGame: Round = {
    val roundResult = Await.result(roundRepository.getLastRoundByGame(game), Duration.Inf)
    if roundResult.isDefined then roundResult.get else null
  }

  def setGame(newGame: Game): Unit = game = newGame
  def getGame: Game = game

end GameController
