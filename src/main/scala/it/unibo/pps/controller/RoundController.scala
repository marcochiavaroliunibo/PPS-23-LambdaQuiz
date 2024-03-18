package it.unibo.pps.controller

import it.unibo.pps.business.RoundRepository
import it.unibo.pps.model.Round

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object RoundController:

  private var round: Round = null
  private val roundRepository = new RoundRepository

  def setRound(newRound: Round): Unit = round = newRound
  
end RoundController
