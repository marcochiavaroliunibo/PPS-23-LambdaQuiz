package model

class Question (id: String, text: String, answer1: String, answer2: String, 
    answer3: String, answer4: String, correctAnswer: Int, category: Category) {
  
    def getID () : String = id
    def getAnswers(): List[String] = List(answer1, answer2, answer3, answer4)
    def getCorrectAnswer () : String = correctAnswer match {
        case 1 => answer1
        case 2 => answer2
        case 3 => answer3
        case 4 => answer4
    }
    def getCategory () : Category = category

}
