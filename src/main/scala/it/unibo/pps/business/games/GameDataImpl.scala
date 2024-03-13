package it.unibo.pps.business.games

import it.unibo.pps.business.ConnectionMongoDB

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import it.unibo.pps.model.User
import scala.concurrent.ExecutionContext.Implicits.global

abstract class GameDataImpl extends GameData {

//    override def getGameInProgressByUser(user: User): Option[Document] = {
//        val filter1 = equal("completed", false)
//        val filter2 = or(equal("user1", user.getID()), equal("user2", user.getID()))
//        val result = collection.find(and(filter1, filter2)).headOption()
//        // Attende il risultato e restituisce l'utente trovato, se presente
//        Await.result(result, Duration.Inf)
//        // todo: convertire de ottenere la stringa
//        // ...
//    }
//
//    override def getLastGameCompletedByUser(user: User): Option[Document] = {
//        val filter1 = equal("completed", true)
//        val filter2 = or(equal("user1", user.getID()), equal("user2", user.getID()))
//        val result = collection.find(and(filter1, filter2))
//            .sort(descending("lastUpdate")).limit(5).headOption()
//        // Attende il risultato e restituisce l'utente trovato, se presente
//        Await.result(result, Duration.Inf)
//        // todo: convertire de ottenere la stringa
//        // ...
//    }
}
