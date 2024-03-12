import org.mongodb.scala.Document
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.mongodb.scala.model.Filters.equal

class CategoryDataImpl extends CategoryData {

    val collection = ConnectionMongoDB.getDatabase().getCollection("categories")

    override def getAllCategories(): Option[Document] = {
        val result = collection.find().headOption()
        // Attende il risultato e restituisce l'utente trovato, se presente
        Await.result(result, Duration.Inf)
        // todo: convertire de ottenere la stringa
        // ...
    }

    override def getCategoryByName(name: String): Option[Document] = {
        val result = collection.find(equal("name", name)).headOption()
        // Attende il risultato e restituisce l'utente trovato, se presente
        Await.result(result, Duration.Inf)
        // todo: convertire de ottenere la stringa
        // ...
    }

}
