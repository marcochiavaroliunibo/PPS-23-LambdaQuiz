package it.unibo.pps.business

import it.unibo.pps.model.{Category, Question}
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import java.util.concurrent.Executors.newSingleThreadExecutor
import scala.concurrent.{ExecutionContext, Future}

/** Classe che rappresenta il repository per l'entità [[Question]].
  *
  * Fornisce metodi più specifici per l'interazione con il database.
  */
class QuestionRepository extends Repository[Question]:

  protected val collection: Future[BSONCollection] = ConnectionMongoDB.getDatabase().map(_.collection("questions"))

  /** Metodo che permette di ottenere tutte le domande di una determinata categoria
    *
    * @param category
    *   categoria delle domande da ottenere
    * @return
    *   lista di domande della categoria specificata
    */
  def getQuestionsByCategory(category: Category): Future[Option[List[Question]]] =
    readMany(
      BSONDocument(
        "category" -> category.toString
      )
    )

end QuestionRepository
