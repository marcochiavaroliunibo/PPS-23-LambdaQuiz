package it.unibo.pps.business.rounds

import it.unibo.pps.business.{ConnectionMongoDB, Repository}
import it.unibo.pps.model.{Game, Round}

import scala.concurrent.ExecutionContext.Implicits.global

trait RoundData extends Repository[Round] {
  def getAllRoundsByGame(game: Game): Option[?]

}
