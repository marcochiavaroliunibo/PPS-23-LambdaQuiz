package it.unibo.pps.view.scenes

import it.unibo.pps.controller.{GameController, UserController}
import it.unibo.pps.model.User
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.UIUtils.{changeScene, craftButton}
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.beans.property.{IntegerProperty, ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ListView, TableColumn, TableView}
import scalafx.scene.layout.*
import scalafx.scene.text.Text

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/** Questa classe rappresenta la pagina di visualizzazione delle statistiche dell'utente. Bisogna selezionare il nome
  * dell'utente per cui vogliamo visualizzare le statistiche
  */
class GlobalRankingScene extends Scene:

  private val user: User = UserController.loggedUsers.get.head

  private val goToBackBtn = craftButton("Indietro")
  goToBackBtn.onAction = _ => changeScene(this.window.get().getScene, MenuScene())

  private val goToReportBtn = craftButton("Le tue statistiche")
  goToReportBtn.onAction = _ => changeScene(this.window.get().getScene, ReportScene())

  private val rankingList = new ListView[String](List.empty) {
    prefWidth = 250
    prefHeight = 250
    cellFactory = (cell, value) => {
      cell.text = value
      cell.style = "-fx-font: normal bold 22px serif"
    }
  }

  private val rankingPane = new VBox(5) {
    alignment = Pos.Center
    children = List(
      new Text("Caricamento in corso...") { style = "-fx-font: normal bold 24px serif" },
      new Text("Non uscire da questa pagina.") { style = "-fx-font: normal normal 18px serif" }
    )
  }

  GameController
    .getGlobalRanking()
    .onComplete {
      case Success(result) =>
        Platform.runLater {
          rankingList.items = ObservableBuffer(result.map(_.username)*)
          rankingPane.children = rankingList
        }
      case Failure(_) =>
        Platform.runLater {
          rankingPane.children = new Text("Errore durante il calcolo della classifica globale. Riprovare!")
        }
    }

  root = new BorderPane {
    top = new Text(s"Ciao ${user.username}, ecco la classifica globale") {
      alignmentInParent = Pos.Center
      font = UIUtils.getSceneTitleFont
    }
    center = rankingPane
    bottom = UIUtils.getFooterWithButtons(goToBackBtn, goToReportBtn)
    background = UIUtils.defaultBackground
  }
end GlobalRankingScene

object GlobalRankingScene:
  def apply(): Scene = new GlobalRankingScene
end GlobalRankingScene
