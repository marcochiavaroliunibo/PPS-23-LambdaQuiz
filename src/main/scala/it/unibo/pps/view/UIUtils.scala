package it.unibo.pps.view

import it.unibo.pps.view.scenes.MenuScene
import javafx.stage.Stage
import scalafx.Includes.*
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.*
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout.*
import scalafx.scene.paint.Color.{Black, DeepSkyBlue, DodgerBlue, Gold, Goldenrod, Green, Red}
import scalafx.scene.paint.{Color, LinearGradient, Paint, Stops}
import scalafx.scene.text.Font

object UIUtils:

  def craftBackground(paint: Paint, round: Int = 0): Background =
    new Background(Array(new BackgroundFill(paint, CornerRadii(round), Insets.Empty)))

  def changeScene(currentScene: Scene, newScene: => Scene): Unit =
    currentScene.window.value.asInstanceOf[Stage].scene = newScene

  def getSceneTitleFont: Font =
    new Font("Verdana", 32)

  def getTextFieldWithPromptedText(t: String): TextField = new TextField() { promptText = t }

  def getPasswordField: PasswordField = new PasswordField() { promptText = "Password" }

  def showSimpleAlert(at: AlertType, m: String): Unit = Alert(at, m, ButtonType.Close).showAndWait()

  def showAlertWithButtons(at: AlertType, m: String, bt: ButtonType*): Option[ButtonType] =
    Alert(at, m, bt*).showAndWait()

  def defaultBackground: Background =
    this.craftBackground(new LinearGradient(endX = 0, stops = Stops(DodgerBlue, DeepSkyBlue)))

  def craftButton(displayName: String, fontSize: Int = 24, widthBtn: Int = 250, heightBtn: Int = 40,
                  colorTop: Color = Gold, colorDown: Color = Goldenrod): Button = new Button {
    text = displayName
    font = new Font("Comic Sans MS", fontSize)
    prefWidth = widthBtn
    prefHeight = heightBtn
    val buttonGradient = new LinearGradient(endX = 0, stops = Stops(colorTop, colorDown))
    background = craftBackground(buttonGradient, 4)
    border = new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, new CornerRadii(8), new BorderWidths(4)))
  }

  def craftButtonWin(displayName: String): Button = craftButton(displayName, 24, 250, 40, Green, Green)
  def craftButtonLose(displayName: String): Button = craftButton(displayName, 24, 250, 40, Red, Red)
  
  def getFooterWithButtons(buttons: Button*): HBox = new HBox(10) {
    alignment = Pos.Center
    children = buttons
  }

  def getPlayersLabels: List[Label] = List("Giocatore 1", "Giocatore 2").map(new Label(_) {
    font = new Font("Arial Bold", 15)
    alignmentInParent = Pos.Center
  })
  
  def getSinglePlayerLabel: Label = new Label("Dati giocatore") {
    font = new Font("Arial Bold", 15)
    alignmentInParent = Pos.Center
  }

  /** verifica che i campi in input siano conformi. In particolare, verifica che non ci siano campi vuoti e che abbiano
    * una lunghezza maggiore o uguale di 6 caratteri .
    * @param textFields
    *   i [[TextField]] contenenti i campi da verificare
    * @return
    *   [[true]] se i campi sono conformi, [[false]] altrimenti
    */
  private def fieldsAreCompliant(textFields: Seq[TextField]): Boolean =
    textFields.map(_.getText).forall(s => s.nonEmpty && s.length >= 6)

  /** Verifica che i campi in input no siano uguali.
    * @param textFields
    *   i [[TextField]] contenenti i campi da verificare
    * @return
    *   [[true]] se i campi sono tutti diversi, [[false]] altrimenti
    */
  private def fieldsAreDistinct(textFields: Seq[TextField]): Boolean =
    textFields.distinct.length == textFields.length

  /** Verifica che le password in input siano uguali.
    * @param passwordFields
    *   i [[TextField]] contenenti le password
    * @return
    *   [[true]] se tutte le password sono identiche, [[false]] altrimenti
    */
  private def passwordFieldsAreIdentical(passwordFields: Seq[TextField]): Boolean =
    passwordFields.map(_.getText) match
      case Seq(p1, p2) => p1 == p2
      case _           => false

  /** Verifica se i campi di input per il login sono validi. In particolare, verifica che gli username e le password
    * siano tutti non vuoti e di lunghezza maggiore o uguale a 6 caratteri e poi controlla che gli username siano
    * diversi l'uno dall'altro e delle password
    * @param usernameFields
    *   i [[TextField]] contenenti i nomi utente
    * @param passwordFields
    *   i [[TextField]] contenenti i le password
    * @return
    *   [[true]] se i campi sono conformi per il processo di login, [[false]] altrimenti
    */
  def areLoginInputsValid(usernameFields: TextField*)(passwordFields: TextField*): Boolean =
    fieldsAreCompliant(usernameFields.appendedAll(passwordFields)) && fieldsAreDistinct(usernameFields)

  /** Verifica se i campi di input per la registrazione sono validi. In particolare, verifica che lo username e le
    * password siano non vuoti e abbiano una lunghezza maggiore o uguale a 6 caratteri. Inoltre, controlla che le due
    * password coincidano
    * @param usernameField
    *   il [[TextField]] contenente il nome utente
    * @param passwordFields
    *   i [[TextField]] contenenti i le password
    * @return
    *   [[true]] se i campi sono conformi per il processo di registrazione, [[false]] altrimenti
    */
  def areRegistrationInputsValid(usernameField: TextField)(passwordFields: TextField*): Boolean =
    fieldsAreCompliant(passwordFields :+ usernameField) && passwordFieldsAreIdentical(passwordFields)

end UIUtils
