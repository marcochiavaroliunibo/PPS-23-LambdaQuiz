package it.unibo.pps.view.components

import it.unibo.pps.model.User
import it.unibo.pps.view.UIUtils
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control.*
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.GridPane

private class LoginReportComponent extends Dialog[List[User]]:
  title = "Finestra di login"
  headerText = "Inserire il nome utente e la password per visualizzare i report"

  // Set the button types.
  private val loginButtonType = new ButtonType("Login", ButtonData.OKDone)
  dialogPane().buttonTypes = Seq(loginButtonType, ButtonType.Cancel)

  // Create the username and password labels and fields.
  private val usernameOfPlayer = UIUtils.getTextFieldWithPromptedText("Username")
  private val passwordOfPlayer = UIUtils.getPasswordField

  private val grid: GridPane = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(20, 50, 10, 10)

    val playerLabel: Label = UIUtils.getSinglePlayerLabel

    add(playerLabel, 1, 0)
    add(new Label("Username:"), 0, 1)
    add(usernameOfPlayer, 1, 1)
    add(new Label("Password:"), 0, 2)
    add(passwordOfPlayer, 1, 2)
  }
  dialogPane().setContent(grid)

  // Request focus on the username field by default.
  Platform.runLater(usernameOfPlayer.requestFocus())

  // When the login button is clicked, convert the result to
  // a username-password-pair.
  resultConverter = {
    case buttonPressedType if buttonPressedType == loginButtonType =>
      if UIUtils.areLoginInputsValid(usernameOfPlayer)(passwordOfPlayer)
      then
        List(
          User(usernameOfPlayer.getText, passwordOfPlayer.getText)
        )
      else List.empty
    case _ => null
  }

end LoginReportComponent

object LoginReportComponent:
  def apply(): Dialog[List[User]] = new LoginReportComponent
end LoginReportComponent
