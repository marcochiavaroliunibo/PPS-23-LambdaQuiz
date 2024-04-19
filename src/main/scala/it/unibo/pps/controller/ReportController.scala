package it.unibo.pps.controller

import it.unibo.pps.model.{Game, Report, User}

import scala.collection.immutable.List
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ReportController:
  /** Higher-order function che restituisce una lista di [[Report]] a partire da una lista di [[Game]] e da uno
    * [[User]].
    *
    * In particolare, consente di calcolare le statistiche partendo dalla lista delle partite di interesse e dall'utente
    * per il quale si vogliono mostrare i risultati.
    *
    * Per specificare entrambi i paarmetri, bisogna usare il currying. Esempi di utilizzo:
    * {{{
    *   // Utilizzo completo con il currying
    *   val rankings: List[Ranking] = getReport(lastFiveCompletedGames)(loggedUser)
    *
    *   // Utilizzo parziale, specificando solo il primo parametro.
    *   val showStatsForUser: User => List[Ranking] = getReport(lastFiveCompletedGames)
    *   val statsOfMario: Text = showStatsForUser(mario)
    * }}}
    */
  private val getReport: List[Game] => User => List[Report] = games =>
    user =>
      games.map(g =>
        val adversary: User = g.players.filter(_.id != user.id).head
        val userPoints: Int = RoundController.computePartialPointsOfUser(user, g)
        val adversaryPoints: Int = RoundController.computePartialPointsOfUser(adversary, g)
        Report(adversary.username, userPoints, adversaryPoints)
      )

  /** Metodo che calcola in maniera asincrona i dati delle statistiche da mostrare.
    * @return
    *   una [[Future]] contenente una tupla di 4 elementi: il numero di partite vinte, il numero di partite perse, , la
    *   lista di [[Report]] per la partita in corso e la lista di [[Report]] per le partite completate.
    */
  def getUserReport: Future[(Int, Int, List[Report], List[Report])] = Future {
    UserController.loggedUsers
      .map(_.head)
      .map(user =>
        val currentGame: List[Game] = GameController.getCurrentGamesFromSinglePlayer(user).getOrElse(List.empty)
        val completedGame: List[Game] = GameController.getLastGameCompletedByUser(user).getOrElse(List.empty)
        val gamesWon: Int = GameController.getGameWonByUser(user)
        val gamesLost: Int = GameController.getGameLostByUser(user)
        val currentMatchRanking = getReport(currentGame)(user)
        val completedMatchesRanking = getReport(completedGame)(user)
        (gamesWon, gamesLost, currentMatchRanking, completedMatchesRanking)
      )
      .getOrElse((0, 0, List.empty, List.empty))
  }

end ReportController
