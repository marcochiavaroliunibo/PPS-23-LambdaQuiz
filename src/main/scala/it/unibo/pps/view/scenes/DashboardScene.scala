package it.unibo.pps.view.scenes

import it.unibo.pps.controller.{GameController, UserController}
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.UIUtils.*
import it.unibo.pps.view.components.CurrentGameStatus
import scalafx.Includes.*
import scalafx.geometry.{Insets, Pos}
import scalafx.scene
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button}
import scalafx.scene.layout.*
import scalafx.scene.text.Text

class DashboardScene extends Scene:
  private val loggedUsers = UserController.loggedUsers.getOrElse(List.empty)
  private val currentGame = GameController.getCurrentGameFromPlayers(loggedUsers)

  private val goBackBtn = craftButton("Indietro")
  goBackBtn.onAction = e => {
    GameController.resetVariable()
    changeScene(this.window.get().getScene, MenuScene())
  }

  private val newMatchBtn = craftButton("Nuovo match")
  newMatchBtn.disable = currentGame.isDefined && !currentGame.get.completed // la partita esiste ed è in corso
  newMatchBtn.onAction = e => {
    GameController.resetVariable()
    GameController.createNewGame()
    showSimpleAlert(AlertType.Confirmation, "Partita creata con successo!")
    changeScene(this.window.get().getScene, DashboardScene())
  }

  private val goToMatchBtn = craftButton("Gioca")
  goToMatchBtn.disable = currentGame.isEmpty || currentGame.get.completed // la partita non esiste o è conclusa
  goToMatchBtn.onAction = _ => changeScene(this.window.get().getScene, QuizScene())

  root = new BorderPane {
    background = defaultBackground
    top = new Text("Dashboard") {
      alignmentInParent = Pos.Center
      font = getSceneTitleFont
    }
    center = CurrentGameStatus(currentGame)
    bottom = new HBox(10) {
      alignment = Pos.Center
      margin = Insets(5)
      children = goBackBtn :: newMatchBtn :: goToMatchBtn :: Nil
    }
  }
end DashboardScene

object DashboardScene:
  def apply(): Scene = new DashboardScene
end DashboardScene
