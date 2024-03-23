package it.unibo.pps.view.scenes

import it.unibo.pps.controller.GameController
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.components.CurrentGameStatus
import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button}
import scalafx.scene.layout.*
import scalafx.scene.text.Text

class DashboardScene extends Scene:
  root = new BorderPane {
    background = UIUtils.defaultBackground
    top = new Text("Dashboard") {
      alignmentInParent = Pos.Center
      font = UIUtils.getSceneTitleFont
    }
    center = CurrentGameStatus()
    bottom = new HBox(10) {
      alignment = Pos.Center
      children = List(
        new Button("nuova partita") {
          onMouseClicked = e => {
            GameController.createNewMatch()
            UIUtils.showSimpleAlert(AlertType.Confirmation, "Partitta creata con successo!")
            UIUtils.changeScene(scene.get(), DashboardScene())
          }
        },
        new Button("Vai alla domanda") {
          onMouseClicked = e => {
            UIUtils.changeScene(scene.get(), QuizScene())
          }
        }
      )
    }
  }
end DashboardScene

object DashboardScene:
  def apply(): Scene = new DashboardScene
end DashboardScene
