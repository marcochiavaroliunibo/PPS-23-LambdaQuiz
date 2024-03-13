package it.unibo.pps.view.components

import scalafx.application.Platform
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.Node
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType}
import scalafx.scene.layout.*
import scalafx.scene.paint.Color.{Black, Gold, Goldenrod}
import scalafx.scene.paint.{LinearGradient, Paint, Stops}
import scalafx.scene.text.Font

/** This object contains the main menu component which displays the game title and the main buttons
  */
object MainMenu extends UIComponent:
  private class MainMenu extends FlowPane(Orientation.Vertical, 0, 10):
    import it.unibo.pps.view.UIUtils.*
    private def craftButton(displayName: String): Button = new Button {
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
      val confirm =
        Alert(AlertType.Confirmation, "Are yoy sure you want to exit the game?", ButtonType.No, ButtonType.Yes)
          .showAndWait()
      confirm.filter(_ == ButtonType.Yes).foreach(_ => Platform.exit())
    }

    menuButtons.filter(_.text.value == "PLAY").head.onAction = _ => {
      LoginComponent.getDialog.showAndWait() match
        case Some(LoginComponent.LoginResult(username, password)) =>
          println(s"$username logged in with password $password")
        case Some(_) | None => println("Dialog returned: None")
    }

    alignment = Pos.Center
    children = menuButtons
  end MainMenu

  private val mainMenu = new MainMenu
  override def getComponent: Node = mainMenu
end MainMenu
