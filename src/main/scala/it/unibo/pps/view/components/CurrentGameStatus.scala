package it.unibo.pps.view.components

import it.unibo.pps.controller.{QuestionController, RoundController}
import it.unibo.pps.model.{Game, User}
import it.unibo.pps.view.UIUtils
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.control.{Label, Separator}
import scalafx.scene.layout.*
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text

private class CurrentGameStatus(currentGame: Option[Game]) extends HBox(10):
  private val playedRounds = RoundController.getPlayedRounds
  private val partialPointsOfUser: User => Int = currentGame
    .map(g => RoundController.computePartialPointsOfUser(_))
    .getOrElse(_ => 0)

  margin = Insets(5)
  background = UIUtils.craftBackground(Color.web("#707070"), 5)
  alignment = Pos.TopCenter
  children = currentGame
    .map(_.players)
    .map(
      _.map(user =>
        new VBox {
          hgrow = Priority.Always
          alignment = Pos.TopCenter
          children = Seq(
            new Label(user.username) { style = "-fx-font: normal bold 24px serif" },
            new Label(s"Punti parziali: ${partialPointsOfUser(user)}") { style = "-fx-font: normal bold 20px serif" },
            new Separator { orientation = Orientation.Horizontal; margin = Insets(5, -5, 5, -5) },
            // qui ci saranno tutti i dati relativi alla partita corrente
            new Label("Rounds") { style = "-fx-font: normal bold 20px serif" },
            new VBox(5) {
              alignment = Pos.TopCenter
              children = playedRounds
                .getOrElse(List.empty)
                .zipWithIndex
                .map((round, i) =>
                  new HBox(3) {
                    margin = Insets(3)
                    val roundResults: List[Rectangle] = round.scores
                      .filter(_.user == user)
                      .filter(_.score != -1)
                      .flatMap(s =>
                        var listBox: List[Rectangle] = List.empty
                        /** aggiungo box verdi per ogni domanda indovinata dall'utente nel round */
                        for (i <- 1 to s.score)
                          listBox = UIUtils.craftRectangle(Color.Green) :: listBox

                        /** aggiungo box rossi per ogni domanda sbagliata dall'utente nel round */
                        for (i <- listBox.length + 1 to QuestionController.QUESTION_FOR_ROUND)
                          listBox = UIUtils.craftRectangle(Color.Red) :: listBox
                        listBox
                      )
                    children =
                      if roundResults.nonEmpty then new Text(s"${i + 1})  ") :: roundResults
                      else List.empty
                  }
                )
            }
          )
        }
      )
    )
    .getOrElse(List(new Text("Non c'è alcuna partita in corso al momento!") {
      style = "-fx-font: normal bold 22px serif"
      margin = Insets(10)
    }))
end CurrentGameStatus

object CurrentGameStatus:
  def apply(g: Option[Game]): CurrentGameStatus = new CurrentGameStatus(g)
end CurrentGameStatus
