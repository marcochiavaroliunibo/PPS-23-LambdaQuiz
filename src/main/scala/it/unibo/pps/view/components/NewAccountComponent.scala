package it.unibo.pps.view.components

import it.unibo.pps.model.User
import it.unibo.pps.view.UIUtils.*
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control.*
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.GridPane

/** Componente grafico utilizzato per la registrazione di un utente.
  *
  * Consiste in un [[Dialog]] che mostra un form da compilare con le informazioni necessarie alla creazione di un nuovo
  * account.
  */
@SuppressWarnings(Array("org.wartremover.warts.Null"))
private class NewAccountComponent extends Dialog[User]:
  title = "Finestra di registrazione"
  headerText = "Inserire il nome utente e la password desiderata"

  // Creazione di un tipo di bottone personalizzato per la funzione di registrazione
  private val accountButtonType = new ButtonType("Registrati", ButtonData.OKDone)
  dialogPane().buttonTypes = Seq(accountButtonType, ButtonType.Cancel)

  // Creazione dei campi per lo username e la password dell'utente da registrare.
  private val username = new TextField() { promptText = "Username" }
  private val password = new PasswordField() { promptText = "Password" }
  private val confirmPassword = new PasswordField() { promptText = "Conferma password" }

  // Form per la registrazione
  private val registrationDataGrid: GridPane = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(20, 50, 10, 10)

    add(getLabel("Dati utente da registrare"), 1, 0)
    add(new Label("Username:"), 0, 1)
    add(username, 1, 1)
    add(new Label("Password:"), 0, 2)
    add(password, 1, 2)
    add(new Label("Conferma password:"), 0, 3)
    add(confirmPassword, 1, 3)
  }
  dialogPane().setContent(registrationDataGrid)

  Platform.runLater(username.requestFocus())

  private val errorMsg = "Campi della registrazione errati. Assicurarsi di aver compilato tutti i campi," +
    "che abbiano una lunghezza di almeno 6 caratteri e che le password siano identiche. Riprovare"

  // Quando viene premuto il pulsante per registrarsi, si converte il risultato
  // di ritorno in una nuova istanza della classe User
  resultConverter = {
    case buttonPressedType if buttonPressedType == accountButtonType =>
      if areRegistrationInputsValid(username)(password, confirmPassword)
      then User(username.getText, password.getText)
      else
        showSimpleAlert(AlertType.Error, errorMsg)
        null
    case _ => null
  }

end NewAccountComponent

/** Factory per le istanze di [[NewAccountComponent]]. */
object NewAccountComponent:
  /** Crea il componente utilizzato per la registrazione degli utenti.
    * @return
    *   una nuova istanza della classe [[NewAccountComponent]] sotto forma di un [[Dialog]]
    */
  def apply(): Dialog[User] = new NewAccountComponent
end NewAccountComponent
