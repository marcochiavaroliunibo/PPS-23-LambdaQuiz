package it.unibo.pps.view

import javafx.stage.Stage
import scalafx.scene.Scene
import scalafx.Includes.*
import scalafx.beans.property.StringProperty
import scalafx.geometry.Insets
import scalafx.scene.control.{PasswordField, TextField}
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii}
import scalafx.scene.paint.Paint
import scalafx.scene.text.Font

object UIUtils:

  def craftBackground(paint: Paint, insets: Int = 0): Background =
    new Background(Array(new BackgroundFill(paint, CornerRadii.Empty, Insets(insets))))

  def changeScene(currentScene: Scene, newScene: => Scene): Unit =
    currentScene.window.value.asInstanceOf[Stage].scene = newScene
    
  def getSceneTitleFont: Font =
    new Font("Verdana", 24)
    
  def getTextFieldWithPromptedText(t: String): TextField = new TextField() { promptText = t }
  
  def getPasswordField: PasswordField = new PasswordField() {promptText = "Password"}

end UIUtils
