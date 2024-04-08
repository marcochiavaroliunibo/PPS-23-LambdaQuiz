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

/** Componente grafico utilizzato per la registrazione di un utente. */
private class NewAccountComponent extends Dialog[User]:
  title = "Finestra di registrazione"
  headerText = "Inserire il nome utente e la password desiderata"

  // Set the button types.
  private val accountButtonType = new ButtonType("Registrati", ButtonData.OKDone)
  dialogPane().buttonTypes = Seq(accountButtonType, ButtonType.Cancel)

  // Create the username and password labels and fields.
  private val username = new TextField() { promptText = "Username" }
  private val password = new PasswordField() { promptText = "Password" }
  private val confirmPassword = new PasswordField() { promptText = "Conferma password" }

  private val grid: GridPane = new GridPane() {
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
  dialogPane().setContent(grid)

  // Request focus on the username field by default.
  Platform.runLater(username.requestFocus())

  // When the login button is clicked, convert the result to
  // a username-password-pair.
  private val errorMsg = "Campi della registrazione errati. Assicurarsi di aver compilato tutti i campi," +
    "che abbiano una lunghezza di almeno 6 caratteri e che le password siano identiche. Riprovare"

  resultConverter = {
    case buttonPressedType if buttonPressedType == accountButtonType =>
      if areRegistrationInputsValid(username)(password, confirmPassword)
      then new User(username.getText, password.getText)
      else
        showSimpleAlert(AlertType.Error, errorMsg)
        null
    case _ => null
  }

end NewAccountComponent

/** Factory for [[NewAccountComponent]] instances. */
object NewAccountComponent:
  def apply(): Dialog[User] = new NewAccountComponent
end NewAccountComponent
