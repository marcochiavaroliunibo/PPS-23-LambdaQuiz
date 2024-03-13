package it.unibo.pps.business.questions

import it.unibo.pps.business.ConnectionMongoDB
import it.unibo.pps.model.Category

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

abstract class QuestionDataImpl extends QuestionData {

//    override def getQuestionsByCategory(category: Category): Option[Document] = {
//        val result = collection.find(equal("idCategory", category.getID())).headOption()
//        // Attende il risultato e restituisce l'utente trovato, se presente
//        Await.result(result, Duration.Inf)
//        // todo: convertire de ottenere la stringa
//        // ...
//    }

}
