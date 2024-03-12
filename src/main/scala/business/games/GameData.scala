import org.mongodb.scala.Document
import model.User

trait GameData {
  
    def getGameInProgressByUser(user: User) : Option[Document]
    def getLastGameCompletedByUser(user: User): Option[Document]

}
