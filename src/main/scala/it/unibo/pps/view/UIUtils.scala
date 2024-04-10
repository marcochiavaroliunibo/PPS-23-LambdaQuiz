package it.unibo.pps.view

import javafx.stage.Stage
import scalafx.Includes.*
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.*
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout.*
import scalafx.scene.paint.Color.{Black, DeepSkyBlue, DodgerBlue, Gold, Goldenrod}
import scalafx.scene.paint.{Color, LinearGradient, Paint, Stops}
import scalafx.scene.shape.{Rectangle, StrokeType}
import scalafx.scene.text.{Font, Text}

/** Contiene una serie di metodi statici che supportano ed organizzano le procedure della View.
  */
object UIUtils:

  /** Restituisce uno sfondo a partire da un [[Paint]], come ad esempio un colore
    * @param paint
    *   oggetto estetico da utilizzare come sfondo. Ad esempio, un [[Color]]
    * @param round
    *   intero che specifica la rotondezza degli angoli dello sfondo. Di default ha un valore pari a [[0]]
    * @return
    *   un'istanza della classe [[Background]] da usare come sfondo nei [[Node]] di ScalaFX
    */
  def craftBackground(paint: Paint, round: Int = 0): Background =
    new Background(Array(new BackgroundFill(paint, CornerRadii(round), Insets.Empty)))

  /** Funzione utile per modificare la schermata da visualizzare all'utente.
    * @param currentScene
    *   schermata corrente
    * @param newScene
    *   schermata che si vuole visualizzare
    */
  def changeScene(currentScene: Scene, newScene: => Scene): Unit =
    currentScene.window.value.asInstanceOf[Stage].scene = newScene

  /** Restituisce il titolo per una [[Scene]] partendo da una stringa, con delle caratteristiche di formattazione ben
    * definite.
    * @param t
    *   la stringa che rappresenta il titolo
    * @return
    *   un'istanza della classe [[Text]]
    */
  def getSceneTitle(t: String): Text = new Text(t) {
    alignmentInParent = Pos.Center
    margin = Insets(5)
    font = new Font("Roboto Bold", 32)
  }

  /** Restituisce un campo di testo, avente una brevissima descrizione sul valore da inserire
    * @param t
    *   la descrizione sul valore che il campo di testo deve ospitare
    * @return
    *   un'istanza della classe [[TextField]]
    */
  def getTextFieldWithPromptedText(t: String): TextField = new TextField() { promptText = t }

  /** @return
    */
  def getPasswordField: PasswordField = new PasswordField() { promptText = "Password" }

  /** Versione semplificata di [[showAlertWithButtons]] che mostra una finestra di avviso con un solo pulsante che ha la
    * funzione di chiuderla.
    */
  def showSimpleAlert(at: AlertType, m: String): Unit = this.showAlertWithButtons(at, m, ButtonType.Close)

  /** Crea e visualizza una finestra di avviso in sovraimpressione con caratteristiche personalizzabili.
    *
    * In particolare, è possibile scegliere la tipologia di avviso da mostrare, un testo descrittivo dello stesso e una
    * serie di pulsanti.
    *
    * @param at
    *   tipologia di avviso da mostrare, appartenente ai valori di [[AlertType]]
    * @param m
    *   testo descrittivo dell'avviso
    * @param bt
    *   pulsanti da aggiungere alla finestra, di tipo [[ButtonType]]
    */
  def showAlertWithButtons(at: AlertType, m: String, bt: ButtonType*): Option[ButtonType] =
    Alert(at, m, bt*).showAndWait()

  /** Viene utilizzato dalle varie schermate dell'applicazione per impostare uno sfondo omogeneo.
    *
    * @return
    *   un oggetto [[Background]] composto da un gradiente verticale di due tonalità del colore blu, dalla più chiara in
    *   alto alla più scura in basso.
    */
  def defaultBackground: Background =
    this.craftBackground(new LinearGradient(endX = 0, stops = Stops(DodgerBlue, DeepSkyBlue)))

  /** Consente di creare un pulsante, specificando il testo da mostrare al suo interno ed il colore di sfondo.
    *
    * Il pulsante restituitò ha delle caratteristiche grafiche già definite, come le dimensioni, il bordo, il font del
    * testo ed un colore di default.
    *
    * @param displayName
    *   testo da mostrare all'interno del pulsante
    * @param gradientColours
    *   i due colori di tipo [[Color]] da usare per lo sfondo del pulsante sotto forma di tupla [[(colore1, colore2)]]
    * @return
    *   un'istanza della classe [[Button]]
    */
  def craftButton(
    displayName: String,
    gradientColours: (Color, Color) = (Gold, Goldenrod)
  ): Button = new Button {
    text = displayName
    font = new Font("Roboto Bold", 22)
    minWidth = 200
    prefHeight = 40
    val buttonGradient = new LinearGradient(endX = 0, stops = Stops(gradientColours._1, gradientColours._2))
    background = craftBackground(buttonGradient, 10)
    border = new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, new CornerRadii(8), new BorderWidths(4)))
  }

  /** Crea la forma geometrica del rettangolo, con un colore di riempimento ed un bordo nero
    * @param c
    *   colore di riempimento per il rettangolo
    * @return
    *   un'istanza della classe [[Rectangle]]
    */
  def craftRectangle(c: Color): Rectangle = new Rectangle {
    width = 30
    height = 20
    fill = c
    stroke = Color.Black
    strokeWidth = 1.5
    strokeType = StrokeType.Inside
  }

  /** Restituisce il componente grafico che funge da footer per alcune schermate dell'applicazione.
    *
    * Esso è costituito da un componente che dispone gli elementi orizzontalmente, uno accanto all'altro.
    *
    * @param buttons
    *   i pulsanti da inserire nel footer
    * @return
    *   un'istanza della classe [[HBox]] che contiene i bottoni passati in input
    */
  def getFooterWithButtons(buttons: Button*): HBox = new HBox(10) {
    margin = Insets(5)
    alignment = Pos.Center
    children = buttons
  }

  /** Restituisce un componente grafico che informa l'utente del fatto che è in corso il caricamento di alcune risorse.
    * @return
    *   un'istanza della classe [[VBox]]
    */
  def getLoadingScreen: VBox = new VBox(5) {
    alignment = Pos.Center
    children = List(
      new Text("Caricamento in corso...") {
        style = "-fx-font: normal bold 24px serif"
      },
      new Text("Non uscire da questa pagina.") {
        style = "-fx-font: normal normal 18px serif"
      }
    )
  }

  /** Funzione per determinare il colore deii pulsanti per le risposte, sulla base dell'indice di quest'ultima.
    * @param an
    *   indice della risposta dal quale si vuole ricavare il colore del rispettivo bottone
    * @return
    *   una tupla formata da due elementi di tipo [[Color]], ch everranno utilizzati per dare al pulsante un colore di
    *   sfondo a gradiente.
    */
  def getAnswerBtnColor(an: Int): (Color, Color) = an match
    case 0 => (Color.web("#FFD700"), Color.web("#F7C200")) // giallo
    case 1 => (Color.web("#FF7F00"), Color.web("#E75700")) // arancio
    case 2 => (Color.web("#00FF00"), Color.web("#00E700")) // verde
    case _ => (Color.web("#A040FF"), Color.web("#8B2BE7")) // viola

  /** Restituisce un'istanza di [[Label]] a partire da una stringa in input, impostando un determinato font e
    * l'allineamento del testo al centro rispetto al componente padre.
    */
  val getLabel: String => Label =
    new Label(_) {
      font = new Font("Arial Bold", 15)
      alignmentInParent = Pos.Center
    }

  /** verifica che i campi in input siano conformi. In particolare, verifica che non ci siano campi vuoti e che abbiano
    * una lunghezza maggiore o uguale di 6 caratteri .
    * @param textFields
    *   i [[TextField]] contenenti i campi da verificare
    * @return
    *   [[true]] se i campi sono conformi, [[false]] altrimenti
    */
  private def fieldsAreCompliant(textFields: Seq[TextField]): Boolean =
    textFields.map(_.getText).forall(s => s.nonEmpty && s.length >= 6)

  /** Verifica che i campi in input no siano uguali.
    * @param textFields
    *   i [[TextField]] contenenti i campi da verificare
    * @return
    *   [[true]] se i campi sono tutti diversi, [[false]] altrimenti
    */
  private def fieldsAreDistinct(textFields: Seq[TextField]): Boolean =
    textFields.distinct.length == textFields.length

  /** Verifica che le password in input siano uguali.
    * @param passwordFields
    *   i [[TextField]] contenenti le password
    * @return
    *   [[true]] se tutte le password sono identiche, [[false]] altrimenti
    */
  private def passwordFieldsAreIdentical(passwordFields: Seq[TextField]): Boolean =
    passwordFields.map(_.getText) match
      case Seq(p1, p2) => p1 == p2
      case _ => false

  /** Verifica se i campi di input per il login sono validi. In particolare, verifica che gli username e le password
    * siano tutti non vuoti e di lunghezza maggiore o uguale a 6 caratteri e poi controlla che gli username siano
    * diversi l'uno dall'altro e delle password
    * @param usernameFields
    *   i [[TextField]] contenenti i nomi utente
    * @param passwordFields
    *   i [[TextField]] contenenti i le password
    * @return
    *   [[true]] se i campi sono conformi per il processo di login, [[false]] altrimenti
    */
  def areLoginInputsValid(usernameFields: TextField*)(passwordFields: TextField*): Boolean =
    fieldsAreCompliant(usernameFields.appendedAll(passwordFields)) && fieldsAreDistinct(usernameFields)

  /** Verifica se i campi di input per la registrazione sono validi. In particolare, verifica che lo username e le
    * password siano non vuoti e abbiano una lunghezza maggiore o uguale a 6 caratteri. Inoltre, controlla che le due
    * password coincidano
    * @param usernameField
    *   il [[TextField]] contenente il nome utente
    * @param passwordFields
    *   i [[TextField]] contenenti i le password
    * @return
    *   [[true]] se i campi sono conformi per il processo di registrazione, [[false]] altrimenti
    */
  def areRegistrationInputsValid(usernameField: TextField)(passwordFields: TextField*): Boolean =
    fieldsAreCompliant(passwordFields :+ usernameField) && passwordFieldsAreIdentical(passwordFields)

end UIUtils
