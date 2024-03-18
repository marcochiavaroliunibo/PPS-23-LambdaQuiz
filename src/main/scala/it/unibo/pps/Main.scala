package it.unibo.pps

import it.unibo.pps.business.ConnectionMongoDB
import it.unibo.pps.view.View

object Main extends App {
  ConnectionMongoDB.initiateDatabaseConnection()
  View.main(Array.empty)
}
