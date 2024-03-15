package it.unibo.pps.view.scenes

import scalafx.beans.property.StringProperty
import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.scene.text.Text

import scala.concurrent.{ExecutionContext, Future}

class DashboardScene(val future: Future[Int]) extends Scene:
    root = new BorderPane {
    top = new Text("Dashboard")
    center = new VBox {
      children = new Text("matches list")
    }
  }
end DashboardScene
