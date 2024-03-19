package it.unibo.pps.view.components

import it.unibo.pps.controller.{GameController, RoundController, UserController}
import it.unibo.pps.view.UIUtils
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.control.{Label, Separator}
import scalafx.scene.layout.*
import scalafx.scene.paint.Color

private class CurrentGameStatus extends HBox(10):
  private val loggedUsers = UserController.getLoggedUsers.getOrElse(List.empty)
  private val currentGame = GameController.getCurrentGameFromPlayers(loggedUsers)
  private val roundsOfCurrentGame = 0

  prefWidth = scene.get().getWindow.getWidth * 0.5
  prefHeight = scene.get().getWindow.getHeight * 0.5
  background = UIUtils.craftBackground(Color(80, 80, 80, 1), 4)
  border = new Border(
    new BorderStroke(Color(80, 80, 80, 1), BorderStrokeStyle.Solid, CornerRadii(4), BorderWidths.Empty)
  )
  alignment = Pos.TopCenter
  children = loggedUsers
    .map(user =>
      new VBox {
        children = Seq(
          new Label(user.getUsername) { style = "-fx-font: normal bold 24px serif" },
          new Separator { orientation = Orientation.Horizontal; margin = Insets(5, 0, 5, 0) },
          // qui ci saranno tutti i dati relativi alla partita corrente
          new Label(s"Punti parziali: $currentGame") { style = "-fx-font: normal bold 24px serif" }
        )
      }
    )
end CurrentGameStatus

object CurrentGameStatus:
  def apply(): CurrentGameStatus = new CurrentGameStatus
end CurrentGameStatus
