package it.unibo.pps.utility

import it.unibo.pps.model.User
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.control.Alert.AlertType
import sun.security.util.Password

object Utility {

  def checkInputLogin(usernameOfPlayer1: String, passwordOfPlayer1: String,
    usernameOfPlayer2: String, passwordOfPlayer2: String): List[User] = {
    if usernameOfPlayer1.isEmpty || passwordOfPlayer1.isEmpty
      || usernameOfPlayer2.isEmpty || passwordOfPlayer2.isEmpty then
      Alert(AlertType.Error, "Compilare tutti i campi per effettuare il login", ButtonType.Close)
        .showAndWait()
      null
    else if usernameOfPlayer1.equals(usernameOfPlayer2) then
      Alert(AlertType.Error, "Inserire due username differenti ed eseguire il login", ButtonType.Close)
        .showAndWait()
      null
    else
      List(
        User(usernameOfPlayer1, passwordOfPlayer1),
        User(usernameOfPlayer2, passwordOfPlayer2)
      )
  }

  def checkInputRegistration(username: String, password: String, confirmPassword: String): User = {
    if username.isEmpty || password.isEmpty || confirmPassword.isEmpty then
      Alert(AlertType.Error, "Compilare tutti i campi richiesti", ButtonType.Close)
        .showAndWait()
      null
    else if !password.equals(confirmPassword) then
      Alert(AlertType.Error, "Le due password non corrispondono", ButtonType.Close)
        .showAndWait()
      null
    else if username.length < 6 then
      Alert(AlertType.Error, "Lo username deve essere lungo almeno 6 caratteri", ButtonType.Close)
        .showAndWait()
      null
    else if password.length < 6 then
      Alert(AlertType.Error, "La password deve essere lunga almeno 6 caratteri", ButtonType.Close)
        .showAndWait()
      null
    else new User(username, password)
  }
  
}
