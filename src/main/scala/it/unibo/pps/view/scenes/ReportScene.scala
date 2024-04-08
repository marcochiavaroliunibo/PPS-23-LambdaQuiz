package it.unibo.pps.view.scenes

import it.unibo.pps.controller.{GameController, RoundController, UserController}
import it.unibo.pps.model.{Game, User}
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

/** Questa classe rappresenta la pagina di visualizzazione delle statistiche dell'utente. Bisogna selezionare il nome
  * dell'utente per cui vogliamo visualizzare le statistiche
  */
class ReportScene extends Scene:
  import it.unibo.pps.view.UIUtils.*
  // TODO: si potrebbe pensare di aggiungere al modello la case class Ranking e creare dei controllers
  //  che contengono i metodi per il calcolo delle classifiche. Forse separeremmo meglio la logica dalla view
  private case class Ranking(playerName: String, playerPoints: Int, adversaryPoints: Int)
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

  private val computeRanking = Future {
    UserController.loggedUsers.map(_.head).map(user =>
      val currentGame: List[Game] = GameController.getCurrentGamesFromSinglePlayer(user).getOrElse(List())
      val completedGame: List[Game] = GameController.getLastGameCompletedByUser(user).getOrElse(List())
      val gamesWon: Int = GameController.getGameWonByUser(user).length
      val gamesLost: Int = GameController.getGameLostByUser(user).length
      val currentMatchRanking = getRankings(currentGame)(user)
      val completedMatchesRanking = getRankings(completedGame)(user)
      (gamesWon, gamesLost, currentMatchRanking, completedMatchesRanking)
    ).getOrElse((0, 0, ObservableBuffer.empty, ObservableBuffer.empty))
  }

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

  private val getTextWithSize: String => Int => Text = t =>
    s =>
      new Text(t) {
        margin = Insets(5, 0, 0, 0)
        wrappingWidth = 450
        textAlignment = TextAlignment.Center
        style = s"-fx-font: normal normal ${s}px sans-serif"
      }
  
  private val rankingScreen = getLoadingScreen

  private val goToBackBtn = craftButton("Indietro")
  goToBackBtn.onAction = _ => changeScene(this.window.get().getScene, MenuScene())

  private val goToGlobalRankingBtn = craftButton("Classifica globale")
  goToGlobalRankingBtn.onAction = _ => changeScene(this.window.get().getScene, GlobalRankingScene())
  
  computeRanking.onComplete {
    case Success(result) =>
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

object ReportScene:
  def apply(): Scene = new ReportScene
end ReportScene
