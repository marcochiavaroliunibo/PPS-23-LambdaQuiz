package it.unibo.pps.view

import javafx.stage.Stage
import scalafx.Includes.*
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType, PasswordField, TextField}
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii}
import scalafx.scene.paint.Color.{DeepSkyBlue, DodgerBlue}
import scalafx.scene.paint.{LinearGradient, Paint, Stops}
import scalafx.scene.text.Font

object UIUtils:

  def craftBackground(paint: Paint, insets: Int = 0): Background =
    new Background(Array(new BackgroundFill(paint, CornerRadii.Empty, Insets(insets))))

  def changeScene(currentScene: Scene, newScene: => Scene): Unit =
    currentScene.window.value.asInstanceOf[Stage].scene = newScene

  def getSceneTitleFont: Font =
    new Font("Verdana", 24)

  def getTextFieldWithPromptedText(t: String): TextField = new TextField() { promptText = t }

  def getPasswordField: PasswordField = new PasswordField() { promptText = "Password" }

  def showSimpleAlert(at: AlertType, m: String): Unit = Alert(at, m, ButtonType.Close).showAndWait()

  def showAlertWithButtons(at: AlertType, m: String, bt: ButtonType*): Option[ButtonType] =
    Alert(at, m, bt*).showAndWait()

  def defaultBackground: Background =
    this.craftBackground(new LinearGradient(endX = 0, stops = Stops(DodgerBlue, DeepSkyBlue)))

end UIUtils
