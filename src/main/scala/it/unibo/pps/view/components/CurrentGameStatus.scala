package it.unibo.pps.view.components

import it.unibo.pps.controller.{GameController, UserController}
import it.unibo.pps.view.UIUtils
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.control.{Label, Separator}
import scalafx.scene.layout.*
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

private class CurrentGameStatus extends HBox(10):
  private val loggedUsers = UserController.loggedUsers.getOrElse(List.empty)
  private val currentGame = GameController.getCurrentGameFromPlayers(loggedUsers)
  case class RounD(p1: List[Int], p2: List[Int])
  private val roundsOfCurrentGameMockup = Seq(
    RounD(List(1, 1, 0), List(1, 1, 0)),
    RounD(List(0, 0, 0), List(0, 1, 0)),
    RounD(List(1, 1, 1), List(0, 0, 1))
  )

  margin = Insets(3)
  background = UIUtils.craftBackground(Color.web("#707070"), 5)
  alignment = Pos.TopCenter
  children = loggedUsers
    .map(user =>
      new VBox {
        hgrow = Priority.Always
        alignment = Pos.TopCenter
        children = Seq(
          new Label(user.getUsername) { style = "-fx-font: normal bold 24px serif" },
          new Label(s"Punti parziali: ${currentGame.getOrElse(0)}") { style = "-fx-font: normal bold 20px serif" },
          new Separator { orientation = Orientation.Horizontal; margin = Insets(5, 0, 5, 0) },
          // qui ci saranno tutti i dati relativi alla partita corrente
          new Label("Rounds") { style = "-fx-font: normal bold 20px serif" },
          new VBox(5) {
            alignment = Pos.TopCenter
            children = roundsOfCurrentGameMockup.map(round =>
              new HBox(2) {
                children = round.p1.map(i => Rectangle(30, 20, if i == 1 then Color.Green else Color.Red))
              }
            )
          }
        )
      }
    )
end CurrentGameStatus

object CurrentGameStatus:
  def apply(): CurrentGameStatus = new CurrentGameStatus
end CurrentGameStatus
