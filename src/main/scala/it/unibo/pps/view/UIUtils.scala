package it.unibo.pps.view

import javafx.stage.Stage
import scalafx.scene.Scene
import scalafx.Includes.*
import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii}
import scalafx.scene.paint.Paint

object UIUtils:

  def craftBackground(paint: Paint, insets: Int = 0): Background =
    new Background(Array(new BackgroundFill(paint, CornerRadii.Empty, Insets(insets))))

  def changeScene(currentScene: Scene, newScene: => Scene): Unit =
    currentScene.window.value.asInstanceOf[Stage].scene = newScene

end UIUtils
