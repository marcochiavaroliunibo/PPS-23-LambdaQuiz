package it.unibo.pps.view.scenes

import it.unibo.pps.controller.{GameController, UserController}
import it.unibo.pps.model.User
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.UIUtils.*
import it.unibo.pps.view.components.CurrentGameStatus
import scalafx.Includes.*
import scalafx.scene
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button}
import scalafx.scene.layout.*

/** Componente grafico che rappresenta la dashboard di gioco, alla quale si accede subito dopo il processo di login.
  * Essa è composta da una sezione in cuo compare la panoramica della partita in corso tra i due giocatori autenticati
  * ed i pulsanti per creare una nuova partita e per accedere alla schermata di gioco.
  */
class DashboardScene extends Scene:
  private val loggedUsers = UserController.loggedUsers.getOrElse(List.empty[User])
  private val currentGame = GameController.getCurrentGameFromPlayers(loggedUsers)

  private val goBackBtn = craftButton("Indietro")
  goBackBtn.onAction = e => {
    GameController.resetVariable()
    changeScene(this.window.get().getScene, MenuScene())
  }

  private val newMatchBtn = craftButton("Nuova partita")
  newMatchBtn.disable = currentGame.isDefined && !currentGame.forall(_.completed) // la partita esiste ed è in corso
  newMatchBtn.onAction = _ => {
    GameController.resetVariable()
    GameController.createNewGame()
    showSimpleAlert(AlertType.Confirmation, "Partita creata con successo!")
    changeScene(this.window.get().getScene, DashboardScene())
  }

  private val goToMatchBtn = craftButton("Gioca")
  goToMatchBtn.disable = currentGame.isEmpty || currentGame.forall(_.completed) // la partita non esiste o è conclusa
  goToMatchBtn.onAction = _ => changeScene(this.window.get().getScene, QuizScene())

  root = new BorderPane {
    background = defaultBackground
    top = getSceneTitle("Dashboard")
    center = CurrentGameStatus(currentGame)
    bottom = getFooterWithButtons(goBackBtn, newMatchBtn, goToMatchBtn)
  }
end DashboardScene

/** Factory per le istanze di [[DashboardScene]]. */
object DashboardScene:
  /** Crea la schermata della dashboard.
    * @return
    *   una nuova istanza della classe [[DashboardScene]] sotto forma di una [[Scene]]
    */
  def apply(): Scene = new DashboardScene
end DashboardScene
