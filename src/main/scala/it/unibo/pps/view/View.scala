package it.unibo.pps.view

import it.unibo.pps.business.ConnectionMongoDB
import it.unibo.pps.view.scenes.MenuScene
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage

object View extends JFXApp3:
  override def start(): Unit =
    stage = new PrimaryStage {
      title = "My app"
      width = 650
      height = 450
      scene = new MenuScene
    }
  
  override def stopApp(): Unit = ConnectionMongoDB.closeConnection()
end View
