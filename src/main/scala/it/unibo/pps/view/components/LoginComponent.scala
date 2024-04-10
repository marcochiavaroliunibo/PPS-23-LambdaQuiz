package it.unibo.pps.view.components

import it.unibo.pps.model.User
import it.unibo.pps.view.UIUtils.*
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control.*
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.GridPane

/** Componente grafico per l'autenticazione degli utenti.
  *
  * Consiste in un [[Dialog]] che contiene i campi da compilare per effettuare l'accesso. Se l'operazione va a buon
  * fine, esso restituisce la lista degli [[User]] che sono stati autenticati.
  *
  * @param singleUserSignIn
  *   booleano che indica se il componente verrà usato per autenticare un singolo utente, oppure no
  */
private class LoginComponent(singleUserSignIn: Boolean) extends Dialog[List[User]]:
  title = "Finestra di login"
  headerText = "Inserire il nome utente e la password dei due giocatori"

  // Creazione di un tipo di bottone personalizzato per la funzione del login
  private val loginButtonType = new ButtonType("Login", ButtonData.OKDone)
  dialogPane().buttonTypes = Seq(loginButtonType, ButtonType.Cancel)

  // Creazione dei campi per lo username e la password dei giocatori.
  private val usernameOfPlayer1 = getTextFieldWithPromptedText("Username")
  private val passwordOfPlayer1 = getPasswordField
  private val usernameOfPlayer2 = getTextFieldWithPromptedText("Username")
  private val passwordOfPlayer2 = getPasswordField

  // Form per il login
  private val loginDataGrid: GridPane = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(20, 50, 10, 10)

    add(new Label("Username:"), 0, 1)
    add(usernameOfPlayer1, 1, 1)
    add(new Label("Password:"), 0, 2)
    add(passwordOfPlayer1, 1, 2)
  }
  dialogPane().setContent(loginDataGrid)

  // Completamento della form in base a se verrà usata da un solo utente oppure da due
  if singleUserSignIn then loginDataGrid.add(getLabel("Dati giocatore"), 1, 0)
  else
    loginDataGrid.add(getLabel("Giocatore 1"), 1, 0)
    loginDataGrid.add(getLabel("Giocatore 2"), 1, 3)
    loginDataGrid.add(new Label("Username:"), 0, 4)
    loginDataGrid.add(usernameOfPlayer2, 1, 4)
    loginDataGrid.add(new Label("Password:"), 0, 5)
    loginDataGrid.add(passwordOfPlayer2, 1, 5)

  Platform.runLater(usernameOfPlayer1.requestFocus())

  // Quando viene premuto il pulsante per il login, si converte il risultato di ritorno in una lista di utenti
  resultConverter = {
    case buttonPressedType if buttonPressedType == loginButtonType =>
      if singleUserSignIn then
        if areLoginInputsValid(usernameOfPlayer1)(passwordOfPlayer1)
        then List(User(usernameOfPlayer1.getText, passwordOfPlayer1.getText))
        else List.empty
      else if areLoginInputsValid(usernameOfPlayer1, usernameOfPlayer2)(passwordOfPlayer1, passwordOfPlayer2)
      then
        List(
          User(usernameOfPlayer1.getText, passwordOfPlayer1.getText),
          User(usernameOfPlayer2.getText, passwordOfPlayer2.getText)
        )
      else List.empty
    case _ => null
  }
end LoginComponent

/** Factory per le istanze di [[LoginComponent]]. */
object LoginComponent:
  /** Crea il componente per il login dei giocatori, che sarà predisposto per l'autenticazione di uno oppure due utenti.
    * @param su
    *   booleano che indica se il componente di login dovrà gestire uno o due utenti
    * @return
    *   una nuova istanza della classe [[LoginComponent]] sotto forma di un [[Dialog]]
    */
  def apply(su: Boolean = false): Dialog[List[User]] = new LoginComponent(su)
end LoginComponent
