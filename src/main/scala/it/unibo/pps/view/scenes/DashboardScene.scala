package it.unibo.pps.view.scenes

import it.unibo.pps.controller.UserController
import it.unibo.pps.view.UIUtils
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.layout.*
import scalafx.scene.paint.Color
import scalafx.scene.text.Text

class DashboardScene extends Scene:
  root = new BorderPane {
    top = new Text("Dashboard") {
      alignmentInParent = Pos.Center
      font = UIUtils.getSceneTitleFont
    }
    center = new HBox(100) {
      alignment = Pos.TopCenter
      children = UserController.getLoggedUsers
        .getOrElse(List.empty)
        .map(user =>
          new VBox(10) {
            alignment = Pos.TopRight
            children = new Text {
              text = user.getUsername
              style = "-fx-font: normal bold 18pt sans-serif"
            }
          }
        )
    }
  }
end DashboardScene
