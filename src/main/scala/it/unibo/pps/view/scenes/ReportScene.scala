package it.unibo.pps.view.scenes

import it.unibo.pps.ECHandler
import it.unibo.pps.controller.{ReportController, UserController}
import it.unibo.pps.model.Report
import it.unibo.pps.view.UIUtils.*
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TableColumn, TableView}
import scalafx.scene.layout.*
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.text.{Text, TextAlignment}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

/** Componente grafico che rappresenta la schermata relativa alle statistiche di un particolare giocatore.
  *
  * Per accedere ad essa, infatti, occorre inserire i dati di accesso dell'utente per il quale si vuole visionare le
  * statistiche.
  *
  * Si compone di tre sezioni principali: una panoramica sul numero di partite vinte e perse, una tabella inerente lo
  * stato attuale dell'eventuale partita in corso e una seconda tabella contenente l'esito delle ultime 5 partite
  * concluse.
  *
  * Nella parte bassa della schermata, è inoltre presente un pulsante per accedere alla classifica globale, gestita dal
  * componente [[GlobalRankingScene]].
  */
class ReportScene extends Scene:

  private val goToBackBtn = craftButton("Indietro")
  goToBackBtn.onAction = _ => changeScene(this.window.get().getScene, MenuScene())

  private val goToGlobalRankingBtn = craftButton("Classifica globale")
  goToGlobalRankingBtn.onAction = _ => changeScene(this.window.get().getScene, GlobalRankingScene())

  /** Genera una tabella che contiene i dati relativi alle statistiche di gioco per l'utente selezionato.
    *
    * In particolare, essa è formata da tre colonne: nome dell'avversario, punteggio dell'utente nella partita contro
    * quell'avversario e punteggio dell'avversario.
    *
    * @param b
    *   lista dei dati da visualizzare, di tipo [[Report]]
    * @return
    *   un'istanza della classe [[TableView]], contenente i dati passati in input
    */
  private def rankingTable(b: ObservableBuffer[Report]): TableView[Report] = {
    val cellStyle = "-fx-font: normal normal 18px sans-serif"
    val playerNameColumn: TableColumn[Report, String] = new TableColumn[Report, String] {
      text = "Nome avversario"
      @SuppressWarnings(Array("org.wartremover.warts.Var"))
      var ranking: Option[Report] = None
      cellValueFactory = d =>
        ranking = Some(d.value)
        ObjectProperty(d.value.adversaryName)
      sortable = false
      cellFactory = (cell, value) => {
        cell.text = ranking.map(_.adversaryName).getOrElse("")
        cell.style = cellStyle
        cell.graphic = ranking
          .map(r =>
            new Circle {
              fill = r match
                case Report(_, up, ap) if up > ap => Color.MediumSeaGreen
                case Report(_, up, ap) if up < ap => Color.LightCoral
                case _ => Color.Orange
              radius = 10
            }
          )
          .getOrElse(new Circle)
      }
    }

    val playerPointsColumn = new TableColumn[Report, Int] {
      text = "Punteggio utente"
      cellValueFactory = d => ObjectProperty(d.value.playerPoints)
      sortable = false
      cellFactory = (cell, value) =>
        cell.text = String.valueOf(value)
        cell.style = cellStyle
    }

    val adversaryPointsColumn = new TableColumn[Report, String] {
      text = "Punteggio avversario"
      cellValueFactory = d => ObjectProperty(d.value.adversaryPoints.toString)
      sortable = false
      cellFactory = (cell, value) =>
        cell.text = value
        cell.style = cellStyle
    }

    new TableView[Report](b) {
      maxWidth = 400
      maxHeight = 350
      columns ++= Seq(playerNameColumn, playerPointsColumn, adversaryPointsColumn)
    }
  }

  /** Higher-order function che restituisce un [[Text]] a partire da una [[String]], che rappresenta il testo, e da un
    * [[Int]], il quale corrisponde alla grandezza dei caratteri.
    *
    * Per specificare entrambi i parametri, bisogna usare il currying. Esempi di utilizzo:
    * {{{
    *   // Utilizzo completo con il currying
    *   val bigText: Text = getTextWithSize("Hello world")(64)
    *
    *   // Utilizzo parziale, specificando solo il primo parametro.
    *   val chooseFontSizeLater: Int => Text = getTextWithSize("choose a font size!")
    *   val smallText: Text = chooseFontSizeLater(12)
    * }}}
    */
  private val getTextWithSize: String => Int => Text = t =>
    s =>
      new Text(t) {
        margin = Insets(5, 0, 0, 0)
        wrappingWidth = 450
        textAlignment = TextAlignment.Center
        style = s"-fx-font: normal normal ${s}px sans-serif"
      }

  private val rankingScreen = getLoadingScreen

  given ExecutionContext = ECHandler.createExecutor
  ReportController.getUserReport
    .onComplete {
      case Success(result) =>
        // Creazione delle tabelle con i dati delle statistiche
        val currentMatchRankingTable = rankingTable(ObservableBuffer(result._3*))
        val completedMatchesRankingTable = rankingTable(ObservableBuffer(result._4*))
        val updatedRankingScene = new VBox(5) {
          alignment = Pos.Center
          children = List(
            getTextWithSize(
              s"Ciao ${UserController.loggedUsers.map(_.head.username).getOrElse("")}, fino ad ora hai vinto ${result._1} partite e ne hai perse ${result._2}"
            )(22),
            getTextWithSize("Statistiche relative alla partita in corso")(18),
            currentMatchRankingTable,
            getTextWithSize("Statistiche relative alle partite concluse")(18),
            completedMatchesRankingTable
          )
        }
        Platform.runLater {
          rankingScreen.children = updatedRankingScene
        }
      case Failure(_) =>
        Platform.runLater {
          rankingScreen.children = new Text("Errore durante il calcolo della classifica. Riprovare!")
        }
    }

  root = new BorderPane {
    top = getSceneTitle("Statistiche giocatore")
    center = rankingScreen
    bottom = getFooterWithButtons(goToBackBtn, goToGlobalRankingBtn)
    background = defaultBackground
  }
end ReportScene

/** Factory per le istanze di [[ReportScene]]. */
object ReportScene:
  /** Crea la schermata della classifiche.
    * @return
    *   una nuova istanza della classe [[ReportScene]] sotto forma di una [[Scene]]
    */
  def apply(): Scene = new ReportScene
end ReportScene
