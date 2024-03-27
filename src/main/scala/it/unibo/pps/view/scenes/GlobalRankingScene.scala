package it.unibo.pps.view.scenes

import it.unibo.pps.controller.{GameController, UserController}
import it.unibo.pps.model.{Game, User}
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.UIUtils.{changeScene, craftButton}
import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.*
import scalafx.scene.text.Text

/** Questa classe rappresenta la pagina di visualizzazione delle statistiche dell'utente.
 *  Bisogna selezionare il nome dell'utente per cui vogliamo visualizzare le statistiche
  */
class GlobalRankingScene extends Scene:

  private val user: User = UserController.loggedUsers.get.head
  
  private val goToBackBtn = craftButton("Indietro")
  goToBackBtn.onAction = _ => changeScene(this.window.get().getScene, MenuScene())

  private val goToReportBtn = craftButton("Le tue statistiche")
  goToReportBtn.onAction = _ => changeScene(this.window.get().getScene, ReportScene())

  private val menuButtons = GameController.getGlobalRanking().map(user =>{
    craftButton(user.username)
  })
  
  root = new BorderPane {
    top = new Text(s"Ciao ${user.username}, ecco la classifica globale") {
      alignmentInParent = Pos.Center
      font = UIUtils.getSceneTitleFont
    }

    center = new VBox(10) {
      alignment = Pos.Center
      children = menuButtons
    }

    bottom = new HBox(10) {
      alignment = Pos.Center
      children = List(goToBackBtn, goToReportBtn)
    }
    background = UIUtils.defaultBackground
  }
end GlobalRankingScene

object GlobalRankingScene:
  def apply(): Scene = new GlobalRankingScene
end GlobalRankingScene
