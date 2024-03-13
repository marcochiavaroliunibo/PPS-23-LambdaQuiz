package it.unibo.pps.business.games

import it.unibo.pps.business.{ConnectionMongoDB, Repository}
import it.unibo.pps.model.{Game, User}

import scala.concurrent.ExecutionContext.Implicits.global

trait GameData extends Repository[Game] {
    def getGameInProgressByUser(user: User) : Option[?]
    def getLastGameCompletedByUser(user: User): Option[?]
}
