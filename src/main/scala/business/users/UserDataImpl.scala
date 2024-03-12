import org.mongodb.scala._
import scala.concurrent._
import scala.concurrent.duration._
import org.mongodb.scala.model.Filters.equal

class UserDataImpl extends UserData {

    val collection = ConnectionMongoDB.getDatabase().getCollection("users")

    override def createUser(nickname: String, password: String): Unit = {
        val newDocument: Document = Document(
            "nickname" -> nickname, 
            "password" -> password
        )
        val insertObservable = collection.insertOne(newDocument)
        val insertResult = insertObservable.toFuture()
        val result = Await.result(insertResult, 10.seconds)
    }

    override def recoveryPassword(nickname: String): Option[Document] = {
        val result = collection.find(equal("nickname", nickname)).first().headOption()
        // Attende il risultato e restituisce l'utente trovato, se presente
        Await.result(result, Duration.Inf)
        // todo: convertire de ottenere la stringa
        // ...
    }

    override def deleteUser(nickname: String): Unit = {
        // Esegue la query per trovare l'utente con il nickname specificato
        val result = collection.deleteOne(equal("nickname", nickname)).toFuture()
        // Attende il risultato dell'operazione di eliminazione
        Await.result(result, Duration.Inf)
    }


}
