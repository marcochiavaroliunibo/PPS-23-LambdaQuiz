import org.mongodb.scala.Document
import model.Game

trait RoundData {
  
    def getAllRoundsByGame(game: Game) : Option[Document]

}
