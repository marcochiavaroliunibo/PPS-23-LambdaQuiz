package it.unibo.pps.view.scenes

import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.components.{GameTitle, MainMenu, UIComponent}
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color.{DeepSkyBlue, DodgerBlue}
import scalafx.scene.paint.{LinearGradient, Stops}

class MenuScene extends Scene:
  root = new BorderPane {
    top = GameTitle.getComponent
    center = MainMenu.getComponent
    background = UIUtils.defaultBackground
  }
end MenuScene

object MenuScene extends UIComponent[Scene]:
  private val menuScene = new MenuScene
  override def getComponent: Scene = menuScene
end MenuScene
