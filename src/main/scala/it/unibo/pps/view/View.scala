package it.unibo.pps.view

import it.unibo.pps.view.components.BasePanel
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene

object View extends JFXApp3:
  override def start(): Unit =
    stage = new PrimaryStage {
      title = "My app"
      width = 650
      height = 450
      scene = new Scene {
        root = BasePanel
      }
    }
end View


