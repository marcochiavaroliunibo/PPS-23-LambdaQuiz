package it.unibo.pps.view.scenes

import it.unibo.pps.controller.{GameController, RoundController, UserController}
import it.unibo.pps.model.{Game, User}
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.UIUtils.{changeScene, craftButton, craftButtonLose, craftButtonWin}
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.layout.*
import scalafx.scene.text.Text
import scalafx.Includes.*
import scalafx.scene.control.Button

/** Questa classe rappresenta la pagina di visualizzazione delle statistiche dell'utente.
 *  Bisogna selezionare il nome dell'utente per cui vogliamo visualizzare le statistiche
  */
class ReportScene extends Scene:

  private val user: User = UserController.loggedUsers.get.head
  private val currentGame: List[Game] = GameController.getCurrentGamesFromSinglePlayer(user).getOrElse(List())
  private val completedGame: List[Game] = GameController.getLastGameCompletedByUser(user).getOrElse(List())
  private val gamesWon: Int = GameController.getGameWonByUser(user).length
  private val gamesLost: Int = GameController.getGameLostByUser(user).length

  private val goToBackBtn = craftButton("Indietro")
  goToBackBtn.onAction = _ => changeScene(this.window.get().getScene, MenuScene())

  private val goToGlobalRankingBtn = craftButton("Classifica globale")
  goToGlobalRankingBtn.onAction = _ => changeScene(this.window.get().getScene, GlobalRankingScene())

  /** lista delle partite in corso */
  private val menuCurrentGame = currentGame.map(game =>
    val his: User = game.players.filter(_.getID != user.getID).head
    val myPoint: Int = RoundController.computePartialPointsOfUser(user, game)
    val hisPoint: Int = RoundController.computePartialPointsOfUser(his, game)
    if myPoint > hisPoint then
      craftButtonWin(s"${his.username} : $myPoint - $hisPoint")
    else if myPoint < hisPoint then
      craftButtonLose(s"${his.username} : $myPoint - $hisPoint")
    else craftButton(s"${his.username} : $myPoint - $hisPoint")
  )

  /** lista delle ultime partite concluse */
  private val menuLastCompletedGame = completedGame.map(game =>
    val his: User = game.players.filter(_.getID != user.getID).head
    val myPoint: Int = RoundController.computePartialPointsOfUser(user, game)
    val hisPoint: Int = RoundController.computePartialPointsOfUser(his, game)
    if myPoint > hisPoint then
      craftButtonWin(s"${his.username} : $myPoint - $hisPoint")
    else if myPoint < hisPoint then
      craftButtonLose(s"${his.username} : $myPoint - $hisPoint")
    else craftButton(s"${his.username} : $myPoint - $hisPoint")
  )

  root = new BorderPane {
    top = new Text(s"Ciao ${user.username}, ecco le tue statistiche") {
      alignmentInParent = Pos.Center
      font = UIUtils.getSceneTitleFont
    }

    center = new VBox(10) {
      alignment = Pos.Center
      children = Seq(
        new Text(s"$gamesWon partite vinte") {
          alignmentInParent = Pos.Center
          font = UIUtils.getSceneTitleFont
        },
        new Text(s"$gamesLost partite perse") {
          alignmentInParent = Pos.Center
          font = UIUtils.getSceneTitleFont
        },
        new HBox(7) {
          alignment = Pos.Center
          children = Seq(
            new VBox(5) {
              alignment = Pos.Center
              children = menuCurrentGame
            },
            new VBox(5) {
              alignment = Pos.Center
              children = menuLastCompletedGame
            }
          )
        }
      )
    }

    bottom = UIUtils.getFooterWithButtons(goToBackBtn, goToGlobalRankingBtn)
    background = UIUtils.defaultBackground
  }
end ReportScene

object ReportScene:
  def apply(): Scene = new ReportScene
end ReportScene
