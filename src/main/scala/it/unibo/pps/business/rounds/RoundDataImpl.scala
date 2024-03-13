package it.unibo.pps.business.rounds

import it.unibo.pps.business.ConnectionMongoDB

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import it.unibo.pps.model.Game


abstract class RoundDataImpl extends RoundData {
    
//    override def getAllRoundsByGame(game: Game): Option[Document] = {
//        val result = collection.find(equal("idGame", game.getID())).headOption()
//        // Attende il risultato e restituisce l'utente trovato, se presente
//        Await.result(result, Duration.Inf)
//        // todo: convertire de ottenere la stringa
//        // ...
//    }

}
