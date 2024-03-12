import org.mongodb.scala.Document
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.mongodb.scala.model.Filters.equal
import model.Category

class QuestionDataImpl extends QuestionData {

    val collection = ConnectionMongoDB.getDatabase().getCollection("questions")

    override def getQuestionsByCategory(category: Category): Option[Document] = {
        val result = collection.find(equal("idCategory", category.getID())).headOption()
        // Attende il risultato e restituisce l'utente trovato, se presente
        Await.result(result, Duration.Inf)
        // todo: convertire de ottenere la stringa
        // ...
    }

}
