package it.unibo.pps.controller

import it.unibo.pps.business.{GameRepository, RoundRepository}
import it.unibo.pps.model.{Game, Round, User}

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
  private def gameOfLoggedUsers_=(g: Game): Unit = _gameOfLoggedUsers = Some(g)

  def getCurrentGameFromPlayers(users: List[User]): Option[Game] = gameOfLoggedUsers match
    case Some(g: Game) => Some(g)
    case None =>
      Await.result(gameRepository.getCurrentGameFromPlayers(users), 5.seconds) match
        case Some(g) => gameOfLoggedUsers = g; Some(g)
        case None    => None

  /** calcola il punteggio parziale di un utente sulla base di tutti i round completati fino a quel momento
    * @param u
    *   l'utente per il quale si vuole ottenere il punteggio parziale
    * @throws IllegalArgumentException
    *   se l'utente in input non viene trovato nel gioco corrente
    * @return
    *   il punteggio parziale per l'utente in input. Restituisce 0 come valore di default.
    */
//  def computePartialPointsOfUser(u: User): Int =
//    val userIndex = gameOfLoggedUsers match
//      case Some(Game(u1, u2, _, _, _, _)) if u1.username == u.username => 1
//      case Some(Game(u1, u2, _, _, _, _)) if u2.username == u.username => 2
//      case _                                                           => throw new IllegalArgumentException
//
//    Await
//      .result(roundRepository.getAllRoundsByGame(gameOfLoggedUsers.get), 5.seconds)
//      .getOrElse(List.empty)
//      .foldLeft(0) {
//        case (points, round) if userIndex == 1 => round.pointUser1 + points
//        case (points, round) if userIndex == 2 => round.pointUser2 + points
//        case _                                 => 0
//      }

end GameController
