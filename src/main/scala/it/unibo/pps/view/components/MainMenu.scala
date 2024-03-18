package it.unibo.pps.view.components

import it.unibo.pps.controller.UserController
import it.unibo.pps.model.User
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.scenes.DashboardScene
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType}
import scalafx.scene.layout.*
import scalafx.scene.paint.Color.{Black, Gold, Goldenrod}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Font

/** Questa classe rappresenta il menu principale, che viene mostrato all'avvio dell'applicazipne Contiene il titolo del
  * gioco ed i pulsanti per giocare e per chiudere il programma
  */
private class MainMenu extends FlowPane(Orientation.Vertical, 0, 10):
  import it.unibo.pps.view.UIUtils.*
  private def craftButton(displayName: String): Button = new Button {
    text = displayName
    font = new Font("Comic Sans MS", 24)
    prefWidth = 250
    prefHeight = 40
    val buttonGradient = new LinearGradient(endX = 0, stops = Stops(Gold, Goldenrod))
    background = craftBackground(buttonGradient, 4)
    border = new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, new CornerRadii(8), new BorderWidths(4)))
  }

  private val menuButtons = Seq("PLAY", "QUIT", "REGISTRATI").map(craftButton)
  menuButtons.filter(_.text.value == "QUIT").head.onAction = _ => {
    UIUtils
      .showAlertWithButtons(
        AlertType.Confirmation,
        "Sei sicuro di voler uscire dal gioco?",
        ButtonType.No,
        ButtonType.Yes
      )
      .filter(_ == ButtonType.Yes)
      .foreach(_ => Platform.exit())
  }

  menuButtons.filter(_.text.value == "PLAY").head.onAction = _ => {
    LoginComponent.getComponent.showAndWait() match
      case Some(List(u1: User, u2: User)) if UserController.checkLogin(List(u1, u2)) =>
        changeScene(scene.get(), new DashboardScene)
      case Some(_) =>
        Alert(AlertType.Error, "Login errato", ButtonType.Close)
          .showAndWait()
      case None =>
  }

  menuButtons.filter(_.text.value == "REGISTRATI").head.onAction = _ => {
    NewAccountComponent.getComponent.showAndWait() match
      case Some(user: User) if UserController.checkUsername(user.getUsername) =>
        UIUtils.showSimpleAlert(AlertType.Error, "Username già esistente")
      case Some(user: User) =>
        try
          UserController.createUser(user)
          UIUtils.showSimpleAlert(AlertType.Confirmation, "Registrazione eseguita!")
        catch case e: Exception => UIUtils.showSimpleAlert(AlertType.Error, "Errore di connessione, riprova tra poco")
      case Some(_) | None =>
  }

  alignment = Pos.Center
  children = menuButtons
end MainMenu

object MainMenu extends UIComponent[FlowPane]:
  private val mainMenu = new MainMenu
  override def getComponent: FlowPane = mainMenu
end MainMenu
