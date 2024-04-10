package it.unibo.pps.view.components

import it.unibo.pps.controller.UserController
import it.unibo.pps.model.User
import it.unibo.pps.view.UIUtils.*
import it.unibo.pps.view.scenes.{DashboardScene, ReportScene}
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType}
import scalafx.scene.layout.*

/** Componente grafico che rappresenta il menu principale, il quale viene mostrato all'avvio dell'applicazipne. Contiene
  * il titolo del gioco ed i pulsanti per accedere alle funzionalità dell'applicazione.
  */
private class MainMenu extends FlowPane(Orientation.Vertical, 0, 10):

  private val errorMsg = "Campi per il login errati. Assicurarsi di aver compilato tutti i campi," +
    "che abbiano una lunghezza di almeno 6 caratteri e che i due username non siano uguali. Riprovare"

  // Gestione pulsante per andare alla dashboard
  private val playBtn: Button = craftButton("DASHBOARD")
  playBtn.onAction = _ => {
    LoginComponent().showAndWait() match
      case Some(List(u1: User, u2: User)) if UserController.checkLogin(List(u1, u2)) =>
        changeScene(scene.get(), DashboardScene())
      case Some(_) => // l'utente ha inserito dei dati che non hanno soddisfatto i criteri di verifica
        showSimpleAlert(AlertType.Error, errorMsg)
      case None => // l'utente ha chiuso il pannello di login
  }

  // Gestione pulsante per aprire le statistiche di un utente
  private val reportBtn: Button = craftButton("STATISTICHE")
  reportBtn.onAction = _ => {
    LoginComponent(true).showAndWait() match
      case Some(List(u: User)) if UserController.checkLogin(List(u)) =>
        changeScene(scene.get(), ReportScene())
      case Some(_) => // l'utente ha inserito dei dati che non hanno soddisfatto i criteri di verifica
        showSimpleAlert(AlertType.Error, errorMsg)
      case None => // l'utente ha chiuso il pannello di login
  }

  // Gestione pulsante per registrare un utente
  private val registerBtn: Button = craftButton("REGISTRATI")
  registerBtn.onAction = _ => {
    NewAccountComponent().showAndWait() match
      case Some(user: User) if UserController.checkUsername(user.username) =>
        showSimpleAlert(AlertType.Error, "Username già esistente, Riprovare!")
      case Some(user: User) =>
        UserController.registerUser(user)
        showSimpleAlert(
          AlertType.Confirmation,
          s"L'utente \"${user.username}\" è stato registrato con successo!"
        )
      case Some(_) | None => // l'utente ha chiuso il pannello di login oppure ha inserito dati non corretti
  }

  // Gestione pulsante per uscire dal gioco
  private val quitBtn: Button = craftButton("ESCI")
  quitBtn.onAction = _ => {
    showAlertWithButtons(
      AlertType.Confirmation,
      "Sei sicuro di voler uscire dal gioco?",
      ButtonType.No,
      ButtonType.Yes
    )
      .filter(_ == ButtonType.Yes)
      .foreach(_ => Platform.exit())
  }

  alignment = Pos.Center
  children = List(playBtn, reportBtn, registerBtn, quitBtn)
end MainMenu

/** Factory per le istanze di [[MainMenu]]. */
object MainMenu:
  /** Crea il componente del menu principale.
    * @return
    *   una nuova istanza di [[MainMenu]] sotto forma di un [[FlowPane]]
    */
  def apply(): FlowPane = new MainMenu
end MainMenu
