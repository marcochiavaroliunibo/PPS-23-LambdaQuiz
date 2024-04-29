package it.unibo.pps

import it.unibo.pps.business.ConnectionMongoDB
import it.unibo.pps.view.View

@main def start(): Unit =
  ConnectionMongoDB.initiateDatabaseConnection()
  View.main(Array.empty)
