package it.unibo.pps.view

import it.unibo.pps.ECHandler
import it.unibo.pps.business.ConnectionMongoDB
import it.unibo.pps.view.scenes.MenuScene
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage

import scala.concurrent.duration.DurationInt

import scala.concurrent.Await

/** Componente grafico che rappresenta il punto di ingresso dell'aplicazione.
  *
  * Crea la finestra principale e visualizza la schermata del menu.
  */
object View extends JFXApp3:
  /** Viene invocato automaticamente da [[JFXApp3]] per avviare la GUI con ScalaFX.
    */
  override def start(): Unit =
    stage = new PrimaryStage {
      title = "LambdaQuiz"
      width = 650
      height = 450
      scene = MenuScene()
    }

  /** Viene invocato automaticamente da [[JFXApp3]] quando la finestra principale viene chiusa.
    *
    * Esegue la disconnessione con il database prima di uscire dal programma.
    */
  override def stopApp(): Unit =
    Await.result(ConnectionMongoDB.closeConnection(), 5.seconds)
    ECHandler.closeAll()
end View
