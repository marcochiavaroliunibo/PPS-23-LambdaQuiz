import org.mongodb.scala.Document
import model.Category

trait QuestionData {
  
    def getQuestionsByCategory(category: Category) : Option[Document]

}
