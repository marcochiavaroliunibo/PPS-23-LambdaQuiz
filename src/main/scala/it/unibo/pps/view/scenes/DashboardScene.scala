package it.unibo.pps.view.scenes

import it.unibo.pps.controller.UserController
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.components.CurrentGameStatus
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.layout.*
import scalafx.scene.paint.Color
import scalafx.scene.text.Text

class DashboardScene extends Scene:
  root = new BorderPane {
    background = UIUtils.defaultBackground
    top = new Text("Dashboard") {
      alignmentInParent = Pos.Center
      font = UIUtils.getSceneTitleFont
    }
    center = CurrentGameStatus()
  }
end DashboardScene
