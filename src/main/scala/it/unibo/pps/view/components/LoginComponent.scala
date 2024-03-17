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

private class LoginComponent extends Dialog[List[User]]:
  title = "Finestra di login"
  headerText = "Inserire il nome utente e la password dei due giocatori"

  // Set the button types.
  private val loginButtonType = new ButtonType("Login", ButtonData.OKDone)
  dialogPane().buttonTypes = Seq(loginButtonType, ButtonType.Cancel)

  // Create the username and password labels and fields.
  private val usernameOfPlayer1 = new TextField() { promptText = "Username" }
  private val passwordOfPlayer1 = new PasswordField() { promptText = "Password" }
  private val usernameOfPlayer2 = new TextField() { promptText = "Username" }
  private val passwordOfPlayer2 = new PasswordField() { promptText = "Password" }

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
    add(usernameOfPlayer1, 1, 1)
    add(new Label("Password:"), 0, 2)
    add(passwordOfPlayer1, 1, 2)

    add(players.last, 1, 3)
    add(new Label("Username:"), 0, 4)
    add(usernameOfPlayer2, 1, 4)
    add(new Label("Password:"), 0, 5)
    add(passwordOfPlayer2, 1, 5)
  }
  dialogPane().setContent(grid)

  // Request focus on the username field by default.
  Platform.runLater(usernameOfPlayer1.requestFocus())

  // When the login button is clicked, convert the result to
  // a username-password-pair.
  resultConverter = {
    case buttonPressedType if buttonPressedType == loginButtonType => {
      checkInputLogin()
    }
    case _ => null
  }
  
  private def checkInputLogin(): List[User] = {
    if usernameOfPlayer1.getText.isEmpty || passwordOfPlayer1.getText.isEmpty
      || usernameOfPlayer2.getText.isEmpty || passwordOfPlayer2.getText.isEmpty then
      Alert(AlertType.Error, "Compilare tutti i campi per effettuare il login", ButtonType.Close)
        .showAndWait()
      null
    else if usernameOfPlayer1.getText.equals(usernameOfPlayer2.getText) then
      Alert(AlertType.Error, "Inserire due username differenti ed eseguire il login", ButtonType.Close)
        .showAndWait()
      null
    else
      List(
        User(usernameOfPlayer1.getText, passwordOfPlayer1.getText),
        User(usernameOfPlayer2.getText, passwordOfPlayer2.getText)
      )
  }
  
end LoginComponent

object LoginComponent extends UIComponent[Dialog[List[User]]]:
  private val loginComponent: LoginComponent = new LoginComponent
  override def getComponent: Dialog[List[User]] = loginComponent
end LoginComponent
