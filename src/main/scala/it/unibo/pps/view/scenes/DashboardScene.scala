package it.unibo.pps.view.scenes

import it.unibo.pps.controller.{GameController, UserController}
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.UIUtils.*
import it.unibo.pps.view.components.CurrentGameStatus
import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.scene
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button}
import scalafx.scene.layout.*
import scalafx.scene.text.Text

class DashboardScene extends Scene:
  private val loggedUsers = UserController.loggedUsers.getOrElse(List.empty)
  private val currentGame = GameController.getCurrentGameFromPlayers(loggedUsers)

  private val newMatchBtn = craftButton("Nuova partita")
  newMatchBtn.disable = currentGame.isDefined
  newMatchBtn.onAction = e => {
    GameController.createNewMatch()
    showSimpleAlert(AlertType.Confirmation, "Partita creata con successo!")
    changeScene(this.window.get().getScene, DashboardScene())
  }
  private val goToMatchBtn = craftButton("Vai alla partita")
  goToMatchBtn.disable = currentGame.isEmpty
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
      children = List(newMatchBtn, goToMatchBtn)
    }
  }
end DashboardScene

object DashboardScene:
  def apply(): Scene = new DashboardScene
end DashboardScene
