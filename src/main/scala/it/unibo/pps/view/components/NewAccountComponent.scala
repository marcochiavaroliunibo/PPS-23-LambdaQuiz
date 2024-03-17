package it.unibo.pps.view.components

import it.unibo.pps.model.User
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.*
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.GridPane
import scalafx.scene.text.Font

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

    val players: Seq[Label] = Seq("Giocatore 1", "Giocatore 2").map(new Label(_) {
      font = new Font("Arial Bold", 15)
      alignmentInParent = Pos.Center
    })

    add(players.head, 1, 0)
    add(new Label("Username:"), 0, 1)
    add(username, 1, 1)
    add(new Label("Password:"), 0, 2)
    add(password, 1, 2)
    add(new Label("Conferma password:"), 0, 5)
    add(confirmPassword, 1, 5)
  }
  dialogPane().setContent(grid)

  // Request focus on the username field by default.
  Platform.runLater(username.requestFocus())

  // When the login button is clicked, convert the result to
  // a username-password-pair.

  resultConverter = {
    case buttonPressedType if buttonPressedType == accountButtonType => {
      checkInputRegistration()
    }
    case _ => null
  }
  
  private def checkInputRegistration(): User = {
    if username.getText.isEmpty || password.getText.isEmpty || confirmPassword.getText.isEmpty then
      Alert(AlertType.Error, "Compilare tutti i campi richiesti", ButtonType.Close)
        .showAndWait()
      null
    else if !password.getText.equals(confirmPassword.getText) then
      Alert(AlertType.Error, "Le due password non corrispondono", ButtonType.Close)
        .showAndWait()
      null
    else if username.getText.length < 6 then
      Alert(AlertType.Error, "Lo username deve essere lungo almeno 6 caratteri", ButtonType.Close)
        .showAndWait()
      null
    else if password.getText.length < 6 then
      Alert(AlertType.Error, "La password deve essere lunga almeno 6 caratteri", ButtonType.Close)
        .showAndWait()
      null
    else new User(username.getText, password.getText)
  }
  
end NewAccountComponent

object NewAccountComponent extends UIComponent[Dialog[User]]:
  private val newAccountComponent: NewAccountComponent = new NewAccountComponent
  override def getComponent: Dialog[User] = newAccountComponent
end NewAccountComponent
