package it.unibo.pps.view.scenes

import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.components.{GameTitle, MainMenu}
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color.{DeepSkyBlue, DodgerBlue}
import scalafx.scene.paint.{LinearGradient, Stops}

class MenuScene extends Scene:
  root = new BorderPane {
    top = GameTitle()
    center = MainMenu()
    background = UIUtils.defaultBackground
  }
end MenuScene

object MenuScene:
  def apply(): Scene = new MenuScene
end MenuScene
