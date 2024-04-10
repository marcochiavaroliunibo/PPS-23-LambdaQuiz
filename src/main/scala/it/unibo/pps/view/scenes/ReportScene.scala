package it.unibo.pps.view.scenes

import it.unibo.pps.controller.{GameController, RoundController, UserController}
import it.unibo.pps.model.{Game, User}
import it.unibo.pps.view.UIUtils.*
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TableColumn, TableView}
import scalafx.scene.layout.*
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.text.{Text, TextAlignment}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/** Componente grafico che rappresenta la schermata relativa alle statistiche di un particolare giocatore.
  *
  * Per accedere ad essa, infatti, occorre inserire i dati di accesso dell'utente per il quale si vuole visionare le
  * statistiche.
  *
  * Si compone di tre sezioni principali: una panoramica sul numero di partite vinte e perse, una tabella inerente lo
  * stato attuale dell'eventuale partita in corso e una seconda tabella contenente l'esito delle ultime 5 partite
  * concluse.
  *
  * Nella parte bassa della schermata, è inoltre presente un pulsante per accedere alla classifica globale, gestita dal
  * componente [[GlobalRankingScene]].
  */
class ReportScene extends Scene:

  private val goToBackBtn = craftButton("Indietro")
  goToBackBtn.onAction = _ => changeScene(this.window.get().getScene, MenuScene())

  private val goToGlobalRankingBtn = craftButton("Classifica globale")
  goToGlobalRankingBtn.onAction = _ => changeScene(this.window.get().getScene, GlobalRankingScene())

  // TODO: si potrebbe pensare di aggiungere al modello la case class Ranking e creare dei controllers
  //  che contengono i metodi per il calcolo delle classifiche. Forse separeremmo meglio la logica dalla view
  private case class Ranking(playerName: String, playerPoints: Int, adversaryPoints: Int)

  /** Higher-order function che restituisce una lista di [[Ranking]] a partire da una lista di [[Game]] e da uno
    * [[User]].
    *
    * In particolare, consente di calcolare le statistiche partendo dalla lista delle partite di interesse e dall'utente
    * per il quale si vogliono mostrare i risultati.
    *
    * Per specificare entrambi i paarmetri, bisogna usare il currying. Esempi di utilizzo:
    * {{{
    *   // Utilizzo completo con il currying
    *   val rankings: ObservableBuffer[Ranking] = getRankings(lastFiveCompletedGames)(loggedUser)
    *
    *   // Utilizzo parziale, specificando solo il primo parametro.
    *   val showStatsForUser: User => ObservableBuffer[Ranking] = getRankings(lastFiveCompletedGames)
    *   val statsOfMario: Text = showStatsForUser(mario)
    * }}}
    */
  private val getRankings: List[Game] => User => ObservableBuffer[Ranking] = games =>
    user =>
      ObservableBuffer(
        games.map(g =>
          val adversary: User = g.players.filter(_.getID != user.getID).head
          val userPoints: Int = RoundController.computePartialPointsOfUser(user, g)
          val adversaryPoints: Int = RoundController.computePartialPointsOfUser(adversary, g)
          Ranking(adversary.username, userPoints, adversaryPoints)
        )*
      )

  /** [[Future]] per calcolare in maniera asincrona i dati delle statistiche da mostrare.
    */
  private val computeRanking = Future {
    UserController.loggedUsers
      .map(_.head)
      .map(user =>
        val currentGame: List[Game] = GameController.getCurrentGamesFromSinglePlayer(user).getOrElse(List())
        val completedGame: List[Game] = GameController.getLastGameCompletedByUser(user).getOrElse(List())
        val gamesWon: Int = GameController.getGameWonByUser(user).length
        val gamesLost: Int = GameController.getGameLostByUser(user).length
        val currentMatchRanking = getRankings(currentGame)(user)
        val completedMatchesRanking = getRankings(completedGame)(user)
        (gamesWon, gamesLost, currentMatchRanking, completedMatchesRanking)
      )
      .getOrElse((0, 0, ObservableBuffer.empty, ObservableBuffer.empty))
  }

  /** Genera una tabella che contiene i dati relativi alle statistiche di gioco per l'utente selezionato.
    *
    * In particolare, essa è formata da tre colonne: nome dell'avversario, punteggio dell'utente nella partita contro
    * quell'avversario e punteggio dell'avversario.
    *
    * @param b
    *   lista dei dati da visualizzare, di tipo [[Ranking]]
    * @return
    *   un'istanza della classe [[TableView]], contenente i dati passati in input
    */
  private def rankingTable(b: ObservableBuffer[Ranking]): TableView[Ranking] = {
    val cellStyle = "-fx-font: normal normal 18px sans-serif"
    val playerNameColumn: TableColumn[Ranking, String] = new TableColumn[Ranking, String] {
      text = "Nome avversario"
      var ranking: Option[Ranking] = None
      cellValueFactory = d =>
        ranking = Some(d.value)
        ObjectProperty(d.value.playerName)
      sortable = false
      cellFactory = (cell, value) => {
        cell.text = ranking.map(_.playerName).getOrElse("")
        cell.style = cellStyle
        cell.graphic = ranking
          .map(r =>
            new Circle {
              fill = r match
                case Ranking(_, up, ap) if up > ap => Color.MediumSeaGreen
                case Ranking(_, up, ap) if up < ap => Color.LightCoral
                case _ => Color.Orange
              radius = 10
            }
          )
          .orNull
      }
    }

    val playerPointsColumn = new TableColumn[Ranking, Int] {
      text = "Punteggio utente"
      cellValueFactory = d => ObjectProperty(d.value.playerPoints)
      sortable = false
      cellFactory = (cell, value) =>
        cell.text = String.valueOf(value)
        cell.style = cellStyle
    }

    val adversaryPointsColumn = new TableColumn[Ranking, String] {
      text = "Punteggio avversario"
      cellValueFactory = d => ObjectProperty(d.value.adversaryPoints.toString)
      sortable = false
      cellFactory = (cell, value) =>
        cell.text = value
        cell.style = cellStyle
    }

    new TableView[Ranking](b) {
      maxWidth = 400
      maxHeight = 350
      columns ++= Seq(playerNameColumn, playerPointsColumn, adversaryPointsColumn)
    }
  }

  /** Higher-order function che restituisce un [[Text]] a partire da una [[String]], che rappresenta il testo, e da un
    * [[Int]], il quale corrisponde alla grandezza dei caratteri.
    *
    * Per specificare entrambi i paarmetri, bisogna usare il currying. Esempi di utilizzo:
    * {{{
    *   // Utilizzo completo con il currying
    *   val bigText: Text = getTextWithSize("Hello world")(64)
    *
    *   // Utilizzo parziale, specificando solo il primo parametro.
    *   val chooseFontSizeLater: Int => Text = getTextWithSize("choose a font size!")
    *   val smallText: Text = chooseFontSizeLater(12)
    * }}}
    */
  private val getTextWithSize: String => Int => Text = t =>
    s =>
      new Text(t) {
        margin = Insets(5, 0, 0, 0)
        wrappingWidth = 450
        textAlignment = TextAlignment.Center
        style = s"-fx-font: normal normal ${s}px sans-serif"
      }

  private val rankingScreen = getLoadingScreen

  computeRanking.onComplete {
    case Success(result) =>
      // creazione delle tabelle con i dati delle statistiche
      val currentMatchRankingTable = rankingTable(result._3)
      val completedMatchesRankingTable = rankingTable(result._4)
      val updatedRankingScene = new VBox(5) {
        alignment = Pos.Center
        children = List(
          getTextWithSize(
            s"Ciao ${UserController.loggedUsers.map(_.head.username).getOrElse("")}, fino ad ora hai vinto ${result._1} partite e ne hai perse ${result._2}"
          )(22),
          getTextWithSize("Statistiche relative alla partita in corso")(18),
          currentMatchRankingTable,
          getTextWithSize("Statistiche relative alle partite concluse")(18),
          completedMatchesRankingTable
        )
      }
      Platform.runLater {
        rankingScreen.children = updatedRankingScene
      }
    case Failure(_) =>
      Platform.runLater {
        rankingScreen.children = new Text("Errore durante il calcolo della classifica. Riprovare!")
      }
  }

  root = new BorderPane {
    top = getSceneTitle("Statistiche giocatore")
    center = rankingScreen
    bottom = getFooterWithButtons(goToBackBtn, goToGlobalRankingBtn)
    background = defaultBackground
  }
end ReportScene

/** Factory per le istanze di [[ReportScene]]. */
object ReportScene:
  /** Crea la schermata della classifiche.
    * @return
    *   una nuova istanza della classe [[ReportScene]] sotto forma di una [[Scene]]
    */
  def apply(): Scene = new ReportScene
end ReportScene
