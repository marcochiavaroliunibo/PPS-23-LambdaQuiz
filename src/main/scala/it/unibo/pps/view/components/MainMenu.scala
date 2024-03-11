package it.unibo.pps.view.components

import scalafx.application.Platform
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType}
import scalafx.scene.layout.{Background, BackgroundFill, Border, BorderStroke, BorderStrokeStyle, BorderWidths, CornerRadii, FlowPane, VBox}
import scalafx.scene.paint.Color.{Black, Gold, Goldenrod}
import scalafx.scene.paint.{LinearGradient, Paint, Stops}
import scalafx.scene.text.Font

object MainMenu extends FlowPane(Orientation.Vertical, 0, 10):
  def craftBackground (paint: Paint): Background =
    new Background(Array(new BackgroundFill(paint, CornerRadii.Empty, Insets(4))))

  def craftButton(displayName: String): Button = new Button {
    text = displayName
    font = new Font("Comic Sans MS", 24)
    prefWidth = 150
    prefHeight = 40
    val buttonGradient = new LinearGradient(endX = 0, stops = Stops(Gold, Goldenrod))
    background = craftBackground(buttonGradient)
    border = new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, new CornerRadii(8), new BorderWidths(4)))
  }


  private val menuButtons = Seq("PLAY", "QUIT").map(craftButton)
  menuButtons.filter(_.text.value == "QUIT").head.onAction = _ => {
    println("WEWE")
    val confirm = Alert(
      AlertType.Confirmation,
      "Are yoy sure you want to exit the game?",
      ButtonType.No, ButtonType.Yes).showAndWait()
    confirm.filter(_ == ButtonType.Yes).foreach(_ => Platform.exit())
  }

  alignment = Pos.Center
  children = menuButtons

end MainMenu

