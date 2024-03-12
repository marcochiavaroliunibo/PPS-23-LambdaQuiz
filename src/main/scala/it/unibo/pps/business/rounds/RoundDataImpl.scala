package it.unibo.pps.business.rounds

import it.unibo.pps.business.ConnectionMongoDB
import org.mongodb.scala.Document

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.mongodb.scala.model.Filters.equal
import model.Game

class RoundDataImpl extends RoundData {

    val collection = ConnectionMongoDB.getDatabase().getCollection("rounds")

    override def getAllRoundsByGame(game: Game): Option[Document] = {
        val result = collection.find(equal("idGame", game.getID())).headOption()
        // Attende il risultato e restituisce l'utente trovato, se presente
        Await.result(result, Duration.Inf)
        // todo: convertire de ottenere la stringa
        // ...
    }

}
