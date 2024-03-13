package it.unibo.pps.business.categories

import it.unibo.pps.business.ConnectionMongoDB

import scala.concurrent.ExecutionContext.Implicits.global

abstract class CategoryDataImpl extends CategoryData {

//    override def getAllCategories(): Option[Document] = {
//        val result = collection.find().headOption()
//        // Attende il risultato e restituisce l'utente trovato, se presente
//        Await.result(result, Duration.Inf)
//        // todo: convertire de ottenere la stringa
//        // ...
//    }
//
//    override def getCategoryByName(name: String): Option[Document] = {
//        val result = collection.find(equal("name", name)).headOption()
//        // Attende il risultato e restituisce l'utente trovato, se presente
//        Await.result(result, Duration.Inf)
//        // todo: convertire de ottenere la stringa
//        // ...
//    }
}
