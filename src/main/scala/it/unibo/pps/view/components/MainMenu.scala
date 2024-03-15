package it.unibo.pps.view.components

import it.unibo.pps.model.User
import it.unibo.pps.view.scenes.DashboardScene
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType}
import scalafx.scene.layout.*
import scalafx.scene.paint.Color.{Black, Gold, Goldenrod}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Font

import scala.concurrent.{ExecutionContext, Future, Promise}

/** Questa classe rappresenta il menu principale, che viene mostrato all'avvio dell'applicazipne Contiene il titolo del
  * gioco ed i pulsanti per giocare e per chiudere il programma
  */
private class MainMenu extends FlowPane(Orientation.Vertical, 0, 10):
  import it.unibo.pps.view.UIUtils.*
  private def craftButton(displayName: String): Button = new Button {
    text = displayName
    font = new Font("Comic Sans MS", 24)
    prefWidth = 150
    prefHeight = 40
    val buttonGradient = new LinearGradient(endX = 0, stops = Stops(Gold, Goldenrod))
    background = craftBackground(buttonGradient, 4)
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
    LoginComponent.getComponent.showAndWait() match
      case Some(users: List[User]) =>
        users.foreach(user => println(s"${user.getName} logged in with password ${user.getPassword}"))
        // changeScene(scene.get(), new DashboardScene(future))
      case Some(_) | None => println("Dialog returned: None")
  }

  alignment = Pos.Center
  children = menuButtons
end MainMenu

object MainMenu extends UIComponent[FlowPane]:
  private val mainMenu = new MainMenu
  override def getComponent: FlowPane = mainMenu
end MainMenu
