package it.unibo.pps.view.components

import it.unibo.pps.controller.UserController
import it.unibo.pps.model.User
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.scenes.DashboardScene
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.layout.*

/** Questa classe rappresenta il menu principale, che viene mostrato all'avvio dell'applicazipne Contiene il titolo del
  * gioco ed i pulsanti per giocare e per chiudere il programma
  */
private class MainMenu extends FlowPane(Orientation.Vertical, 0, 10):
  import it.unibo.pps.view.UIUtils.*

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

  private val errorMsg = "Campi per il login errati. Assicurarsi di aver compilato tutti i campi," +
    "che abbiano una lunghezza di almeno 6 caratteri e che i due username non siano uguali. Riprovare"
  menuButtons.filter(_.text.value == "PLAY").head.onAction = _ => {
    LoginComponent().showAndWait() match
      case Some(List(u1: User, u2: User)) if UserController.checkLogin(List(u1, u2)) =>
        changeScene(scene.get(), DashboardScene())
      case Some(_) => // the user prompted wrong data
        UIUtils.showSimpleAlert(AlertType.Error, errorMsg)
      case None => // the user closed the login dialog
  }

  menuButtons.filter(_.text.value == "REGISTRATI").head.onAction = _ => {
    val res = NewAccountComponent().showAndWait()
    println(res)
    println(res.getOrElse(0))
    res match
      case Some(user: User) if UserController.checkUsername(user.getUsername) =>
        UIUtils.showSimpleAlert(AlertType.Error, "Username già esistente, sceglierne un altro!")
      case Some(user: User) =>
        try
          UserController.registerUser(user)
          UIUtils.showSimpleAlert(AlertType.Confirmation, s"L'utente \"${user.username}\" è stato registrato con successo!")
        catch case e: Exception => UIUtils.showSimpleAlert(AlertType.Error, "Errore di connessione, riprova tra poco")
      case Some(_) | None => // the user closed the login dialog or entered wrong data
  }

  alignment = Pos.Center
  children = menuButtons
end MainMenu

object MainMenu:
  def apply(): FlowPane = new MainMenu
end MainMenu
