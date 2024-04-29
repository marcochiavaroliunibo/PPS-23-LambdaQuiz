package it.unibo.pps.view.scenes

import it.unibo.pps.ECHandler
import it.unibo.pps.controller.GameController
import it.unibo.pps.model.User
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.UIUtils.{changeScene, craftButton, getLoadingScreen}
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ListView}
import scalafx.scene.layout.*
import scalafx.scene.text.Text

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

/** Componente grafico che rappresenta la classifica globale di tutti i giocatori.
  *
  * In particolare, essa coincide con la lista dei 5 giocatori che hanno vinto più partite in assoluto.
  */
class GlobalRankingScene extends Scene:
  private val goToBackBtn = craftButton("Indietro")
  goToBackBtn.onAction = _ => changeScene(this.window.get().getScene, MenuScene())

  private val goToReportBtn = craftButton("Le tue statistiche")
  goToReportBtn.onAction = _ => changeScene(this.window.get().getScene, ReportScene())

  /** [[ListView]] che mostra la classifica globale. */
  private val rankingList = new ListView[String](List.empty) {
    maxWidth = 250
    maxHeight = 250
    cellFactory = (cell, value) => {
      cell.text = value
      cell.alignment = Pos.Center
      cell.style = "-fx-font: normal bold 22px sans-serif"
    }
  }

  // Schermata di caricamento
  private val rankingScreen = getLoadingScreen

  given ExecutionContext = ECHandler.createExecutor
  GameController
    .getGlobalRanking()
    .onComplete {
      case Success(result) =>
        Platform.runLater {
          rankingList.items = ObservableBuffer(result.map(_.username)*)
          rankingScreen.children = rankingList
        }
      case Failure(_) =>
        Platform.runLater {
          rankingScreen.children = new Text("Errore durante il calcolo della classifica globale. Riprovare!")
        }
    }

  root = new BorderPane {
    top = UIUtils.getSceneTitle("Classifica globale")
    center = rankingScreen
    bottom = UIUtils.getFooterWithButtons(goToBackBtn, goToReportBtn)
    background = UIUtils.defaultBackground
  }
end GlobalRankingScene

/** Factory per le istanze di [[GlobalRankingScene]]. */
object GlobalRankingScene:
  /** Crea il componente che mostra la classifica globale.
    * @return
    *   una nuova istanza della classe [[GlobalRankingScene]] sotto forma di una [[Scene]]
    */
  def apply(): Scene = new GlobalRankingScene
end GlobalRankingScene
