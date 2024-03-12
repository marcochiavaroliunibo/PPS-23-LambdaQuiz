package it.unibo.pps.view.components

import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control.*
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.GridPane

object LoginComponent extends DialogComponent:
  // TODO da cambiare con User
  case class LoginResult(username: String, password: String)
  private class LoginComponent extends Dialog[LoginResult]:
    title = "Login Dialog"
    headerText = "Look, a Custom Login Dialog"
  
    // Set the button types.
    private val loginButtonType = new ButtonType("Login", ButtonData.OKDone)
    dialogPane().buttonTypes = Seq(loginButtonType, ButtonType.Cancel)
  
    // Create the username and password labels and fields.
    private val username = new TextField() { promptText = "Username" }
    private val password = new PasswordField() { promptText = "Password" }
  
    private val grid = new GridPane() {
      hgap = 10
      vgap = 10
      padding = Insets(20, 100, 10, 10)
  
      add(new Label("Username:"), 0, 0)
      add(username, 1, 0)
      add(new Label("Password:"), 0, 1)
      add(password, 1, 1)
    }
  
    // Enable/Disable login button depending on whether a username was
    // entered.
    private val loginButton = dialogPane().lookupButton(loginButtonType)
    loginButton.setDisable(true)
  
    // Do some validation (disable when username is empty).
    username.text.onChange { (_, _, newValue) =>
      loginButton.setDisable(newValue.trim().isEmpty)
    }
  
    dialogPane().setContent(grid)
  
    // Request focus on the username field by default.
    Platform.runLater(username.requestFocus())
  
    // When the login button is clicked, convert the result to
    // a username-password-pair.
    resultConverter = {
      case buttonPressedType if buttonPressedType == loginButtonType =>
        LoginResult(username.text(), password.text())
      case _ => null
    }
  end LoginComponent
  
  private val loginComponent: LoginComponent = new LoginComponent
  override def getDialog: Dialog[LoginResult] = loginComponent
end LoginComponent



