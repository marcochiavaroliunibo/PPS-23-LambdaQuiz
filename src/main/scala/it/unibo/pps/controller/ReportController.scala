package it.unibo.pps.controller

import it.unibo.pps.ECHandler
import it.unibo.pps.model.{Game, Report, User}

import scala.collection.immutable.List
import scala.concurrent.{ExecutionContext, Future}

/** Controller che si occupa di gestire la creazione dei report da mostrare all'utente.
  *
  * In particolare, permette di calcolare le statistiche relative alle partite giocate dall'utente, mostrando il nome
  * dell'avversario, i punti ottenuti dall'utente e i punti ottenuti dall'avversario.
  */
object ReportController:
  given ExecutionContext = ECHandler.createExecutor

  /** Higher-order function che restituisce una lista di [[Report]] a partire da una lista di [[Game]] e da uno
    * [[User]].
    *
    * In particolare, consente di calcolare le statistiche partendo dalla lista delle partite di interesse e dall'utente
    * per il quale si vogliono mostrare i risultati.
    *
    * Per specificare entrambi i parametri, bisogna usare il currying. Esempi di utilizzo:
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
        val adversary = g.players.find(_.id != user.id)
        val userPoints: Int = RoundController.computePartialPointsOfUser(user, Some(g))
        val adversaryPoints: Int = adversary.map(RoundController.computePartialPointsOfUser(_, Some(g))).getOrElse(0)
        Report(adversary.map(_.username).getOrElse(""), userPoints, adversaryPoints)
      )

  /** Metodo che calcola in maniera asincrona i dati delle statistiche da mostrare.
    * @return
    *   una [[Future]] contenente una tupla di 4 elementi: il numero di partite vinte, il numero di partite perse, , la
    *   lista di [[Report]] per la partita in corso e la lista di [[Report]] per le partite completate.
    */
  def getUserReport: Future[(Int, Int, List[Report], List[Report])] = Future {
    UserController.loggedUsers
      .flatMap(_.headOption)
      .map(user =>
        val currentGame: List[Game] = GameController.getCurrentGamesFromSinglePlayer(user).getOrElse(List.empty[Game])
        val completedGame: List[Game] = GameController.getLastCompletedGamesByUser(user).getOrElse(List.empty[Game])
        val gamesWon: Int = GameController.getGameWonByUser(user)
        val gamesLost: Int = GameController.getGameLostByUser(user)
        val currentMatchRanking = getReport(currentGame)(user)
        val completedMatchesRanking = getReport(completedGame)(user)
        (gamesWon, gamesLost, currentMatchRanking, completedMatchesRanking)
      )
      .getOrElse((0, 0, List.empty[Report], List.empty[Report]))
  }

end ReportController
