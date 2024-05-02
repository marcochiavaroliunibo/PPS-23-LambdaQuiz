package it.unibo.pps

import it.unibo.pps.business.ConnectionMongoDB
import it.unibo.pps.view.View

/** Metodo di avvio dell'applicazione */
@main def start(): Unit =
  ConnectionMongoDB.initiateDatabaseConnection()
  View.main(Array.empty)
