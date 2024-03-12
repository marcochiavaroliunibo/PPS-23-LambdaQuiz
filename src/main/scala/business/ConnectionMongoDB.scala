/* Questa classe si occupa di fornire i servizi di connessione
al database MongoDB */
import org.mongodb.scala._

object ConnectionMongoDB {
  
    // Variabili per la connessione
    val stringConnection = "mongodb+srv://user-login:marco1234@cluster0.9jwsjr8.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    val mongoClient: MongoClient = MongoClient(stringConnection)
    val database: MongoDatabase = mongoClient.getDatabase("LambdaQuiz")

    def getDatabase() : MongoDatabase = database

    def closeConnection(): Unit = mongoClient.close()

}
